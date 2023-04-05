package com.example.facade;

import com.example.exceptions.*;
import com.example.modele.*;
import com.example.modele.DTO.EmailDTO;
import com.example.producer.RabbitMQProducer;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component("facadeApplication")
public class FacadeApplicationImpl implements FacadeApplication{
    @Autowired
    ConsultationRepository consultationRepository;
    @Autowired
    CreneauRepository creneauRepository;
    @Autowired
    MedecinRepository medecinRepository;
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    private final RabbitMQProducer rabbitMQProducer;


    public FacadeApplicationImpl(PatientRepository patientRepository, MedecinRepository medecinRepository, ConsultationRepository consultationRepository, CreneauRepository creneauRepository, RabbitMQProducer rabbitMQProducer) {
        this.patientRepository = patientRepository;
        this.medecinRepository = medecinRepository;
        this.consultationRepository = consultationRepository;
        this.creneauRepository = creneauRepository;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @Override
    public Medecin ajouterMedecin(String prenom, String nom, String email) throws AdresseMailDejaUtiliseeException {
        if(medecinRepository.existsByEmail(email)){
            throw new AdresseMailDejaUtiliseeException();
        }else{
            Medecin medecin =  new Medecin(prenom, nom, email);
            Medecin nouveauMedecin =  medecinRepository.save(medecin);
            return nouveauMedecin;
        }
    }
    @Override
    public Patient ajouterPatient(String prenom, String nom, String email, String numSecu, String numTel, String dateNaissance, String genre) throws NumeroSecuDejaAttribueException {
        if(patientRepository.existsByNumSecu(numSecu)){
            throw new NumeroSecuDejaAttribueException();
        }else{
            Patient patient =  new Patient(prenom, nom, email, numSecu, numTel, dateNaissance, genre);
            patientRepository.save(patient);
            return patient;
        }
    }
    @Override
    public void modifierAntecedentsPatient(String numSecu, String antecedents) throws PatientInexistantException {
        Patient pa = getPatientByNumSecu(numSecu);
        if (pa != null){
            pa.setAntecedents(antecedents);
            patientRepository.save(pa);
        }else {
            throw new PatientInexistantException();
        }
    }
    @Override
    public void assignerMedecinTraitant(String numSecu, String prenom, String nom) throws PatientInexistantException, MedecinInexistantException {
        Patient p = patientRepository.findPatientByNumSecu(numSecu);
        if(p==null){
            throw new PatientInexistantException();
        }
        Medecin m = medecinRepository.findMedecinByPrenomAndNom(prenom, nom);
        if (m==null){
            throw new MedecinInexistantException();
        }
        p.setIdMedecinTraitant(m.getId());
        m.ajouterPatient(p);
        patientRepository.save(p);
        medecinRepository.save(m);
    }

    @Override
    public Consultation prendreRDV(Patient patient, String dateRDV, String heureRDV, String motif, String ordonnance, String type) throws TypeConsultationInexistantException, CreneauIndisponibleException, PasDeMedecinTraitantAssigneException {
        Medecin medecin = getMedecinTraitant(patient.getNumSecu());
        if(medecin==null){
            throw new PasDeMedecinTraitantAssigneException();
        }
        List<TypeCons> typePossible = Arrays.asList(TypeCons.values());
        if(typePossible.contains(TypeCons.valueOf(type))){
            Creneau creneau;
            if (!(creneauRepository.existsByDateAndHeure(dateRDV, heureRDV))){//Creneau inexistant
                creneau = new Creneau(dateRDV, heureRDV);
                creneau.setDisponibilite(false);
                creneauRepository.save(creneau);
            }else{//Creneau existant
                if ((creneauRepository.findByDateAndHeure(dateRDV, heureRDV).estDispo())){//Existant et dispo
                    creneau = creneauRepository.findByDateAndHeure(dateRDV, heureRDV);
                    creneau.setDisponibilite(false);
                    creneauRepository.save(creneau);
                }else{//Existant mais indispo
                    throw new CreneauIndisponibleException();
                }
            }
            Consultation consultation = new Consultation(creneau, motif, TypeCons.valueOf(type), ordonnance, medecin.getId(), patient.getId());
            medecin.ajouterConsultation(consultation);
            consultationRepository.save(consultation);
            medecinRepository.save(medecin);
            return consultation;
        }else{
            throw new TypeConsultationInexistantException();
        }
    }

    @Override
    public Collection<String> voirProduitsConsultation(int idConsultation) {
        Consultation consultation = consultationRepository.findConsultationById(idConsultation);
        return consultation.getListeProduitsMedicaux();
    }

    @Override
    public Collection<Patient> voirTousLesPatientsMedecin(int idMedecin) {
        Medecin medecin = medecinRepository.findMedecinById(idMedecin);
        Collection<Patient> res = new ArrayList<>();
        medecin.getListePatients().forEach(idPatient->{
            res.add(patientRepository.findPatientById(idPatient));
        });
        return res;
    }

    @Override
    public void utiliserProduit(int idConsultation, String nomProduit) {
        Consultation consultation = consultationRepository.findConsultationById(idConsultation);
        consultation.addProduitMedical(nomProduit);
    }

    @Override
    public Collection<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    @Override
    public Collection<Consultation> getAllConsultationsParType(String type) {
        return consultationRepository.findAllConsultationsByType(TypeCons.valueOf(type));
    }

    @Override
    public void confirmerRDV(int idConsultation) throws ConsultationInexistanteException, ConsultationDejaConfirmeeException {
        if(consultationRepository.existsById(idConsultation)){
            Consultation consultation = consultationRepository.findConsultationById(idConsultation);
            if(!(consultation.estConfirme())){
                consultation.setConfirmation(true);
                consultationRepository.save(consultation);
                Patient patient = patientRepository.findPatientById(consultation.getIdPatient());
                //-------RabbitMQ-------
                EmailDTO email = new EmailDTO();
                email.setDestinataire(patient.getEmail());
                email.setObjet("Confirmation de RDV");
                email.setContenu("Votre consultation n°"+idConsultation+" a bien été confirmée !");
                //Type ?
                this.rabbitMQProducer.sendEmail(email);
                //----------------------
            }else{
                throw new ConsultationDejaConfirmeeException();
            }
        }else{
            throw new ConsultationInexistanteException();
        }
    }
    @Override
    public void modifierCRConsultation(int idConsultation, String compteRendu) throws ConsultationInexistanteException {
        if(consultationRepository.existsById(idConsultation)){
            Consultation consultation = consultationRepository.findConsultationById(idConsultation);
            consultation.setCompteRendu(compteRendu);
            consultationRepository.save(consultation);
        }else{
            throw new ConsultationInexistanteException();
        }
    }
    @Override
    public void annulerConsultation(int idConsultation) throws MedecinInexistantException, ConsultationInexistanteException {
        if(consultationRepository.existsById(idConsultation)){
            Consultation consultation = consultationRepository.findConsultationById(idConsultation);
            Creneau creneau = consultation.getCreneau();
            if (medecinRepository.existsById(consultation.getIdMedecin())){
                Medecin medecin = medecinRepository.findMedecinById(consultation.getIdMedecin());
                medecin.retirerConsultation(idConsultation);
                medecinRepository.save(medecin);
                creneau.setDisponibilite(true);
                creneauRepository.save(creneau);
                consultationRepository.removeConsultationById(idConsultation);
                //Envoyer notif à Medecin(email)
            }else{
                throw new MedecinInexistantException();
            }
        }else {
            throw new ConsultationInexistanteException();
        }
    }
    @Override
    public List<Consultation> voirConsultationsMedecin(int idMedecin) throws MedecinInexistantException, ConsultationInexistanteException, PasDeConsultationAssigneAuMedecinException {
        if(medecinRepository.existsById(idMedecin)){
            Medecin medecin = medecinRepository.findMedecinById(idMedecin);
            List<Consultation> reponse = new ArrayList<>();
            if (medecin.getListeConsultations().size()!=0){
                for (int idConsult: medecin.getListeConsultations()) {
                    if (consultationRepository.existsById(idConsult)){
                        reponse.add(consultationRepository.findConsultationById(idConsult));
                    }
                    else {
                        throw new ConsultationInexistanteException();
                    }
                }
            }else {
                throw new PasDeConsultationAssigneAuMedecinException();
            }

            return reponse;
        }else{
            throw new MedecinInexistantException();
        }
    }
    @Override
    public Medecin getMedecinTraitant(String numSecu){
        return medecinRepository.findMedecinById(patientRepository.findPatientByNumSecu(numSecu).getIdMedecinTraitant());
    }
    @Override
    public void deleteConsultationByID(int idConsultation) throws ConsultationInexistanteException {
        if(consultationRepository.existsById(idConsultation)){
            consultationRepository.removeConsultationById(idConsultation);
        }else{
            throw new ConsultationInexistanteException();
        }
    }
    @Override
    public void deleteMedecinByID(int idMedecin) throws MedecinInexistantException {
        if(medecinRepository.existsById(idMedecin)){
            medecinRepository.removeById(idMedecin);
        }else{
            throw new MedecinInexistantException();
        }
    }
    @Override
    public void deletePatientByID(int idPatient) throws PatientInexistantException {
        if(patientRepository.existsById(idPatient)){
            patientRepository.removePatientById(idPatient);
        }else{
            throw new PatientInexistantException();
        }
    }
    @Override
    public Patient getPatientByEmail(String email) throws PatientInexistantException {
        if (patientRepository.existsByEmail(email)){
            return patientRepository.findPatientByEmail(email);
        }else{
            throw new PatientInexistantException();
        }
    }
    @Override
    public Patient getPatientByNumSecu(String numSecu) throws PatientInexistantException {
        if(patientRepository.existsByNumSecu(numSecu)){
            return patientRepository.findPatientByNumSecu(numSecu);
        }else{
            throw new PatientInexistantException();
        }
    }
}
