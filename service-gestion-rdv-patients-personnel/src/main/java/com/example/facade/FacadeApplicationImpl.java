package com.example.facade;

import com.example.exceptions.*;
import com.example.modele.*;
import com.example.modele.DTO.EmailDTO;
import com.example.modele.DTO.FactureDTO;
import com.example.producer.RabbitMQProducer;
import com.example.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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
    public Patient ajouterPatient(String prenom, String nom, String email, String numSecu, String numTel, String dateNaissance, String genre) throws NumeroSecuDejaAttribueException, AdresseMailDejaUtiliseeException {
        if(patientRepository.existsByNumSecu(numSecu)){
            throw new NumeroSecuDejaAttribueException();
        }else if (patientRepository.existsByEmail(email)){
            throw new AdresseMailDejaUtiliseeException();
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
        }else{
            throw new PatientInexistantException();
        }
    }
    @Override
    public void assignerMedecinTraitant(String numSecu, String prenom, String nom) throws PatientInexistantException, MedecinInexistantException, PatientDejaAttribueAuMedecinException {
        Patient p = patientRepository.findPatientByNumSecu(numSecu);
        Medecin m = medecinRepository.findMedecinByPrenomAndNom(prenom, nom);
        if(p==null){
            throw new PatientInexistantException();
        }
        if (m==null){
            throw new MedecinInexistantException();
        }
        if (m.getListePatients().contains(p.getId())){
            throw new PatientDejaAttribueAuMedecinException();
        }
        m.ajouterPatient(p);
        p.setIdMedecinTraitant(m.getId());
        patientRepository.save(p);
        medecinRepository.save(m);
    }
    @Override
    public Consultation prendreRDV(Patient patient, String dateRDV, String heureRDV, String motif, String type) throws TypeConsultationInexistantException, CreneauIndisponibleException, PasDeMedecinTraitantAssigneException, PatientInexistantException {
        Medecin medecin = getMedecinTraitant(patient.getNumSecu());
        List<String> typePossible = new ArrayList<>();
        for (TypeCons typec:Arrays.asList(TypeCons.values())){
            typePossible.add(typec.toString());
        }
        if(typePossible.contains(type)){
            Creneau creneau;
            if (!(creneauRepository.existsByDateAndHeure(dateRDV, heureRDV))){
                creneau = new Creneau(dateRDV, heureRDV);
                creneau.setDisponibilite(false);
                creneauRepository.save(creneau);
            }else{
                if ((creneauRepository.findByDateAndHeure(dateRDV, heureRDV).estDispo())){
                    creneau = creneauRepository.findByDateAndHeure(dateRDV, heureRDV);
                    creneau.setDisponibilite(false);
                    creneauRepository.save(creneau);
                }else{
                    throw new CreneauIndisponibleException();
                }
            }
            Consultation consultation = new Consultation(creneau, motif, TypeCons.valueOf(type), medecin.getId(), patient.getId());
            medecin.ajouterConsultation(consultation);
            consultationRepository.save(consultation);
            medecinRepository.save(medecin);
            //-------RabbitMQ-------
            EmailDTO email = new EmailDTO();
            email.setDestinataire(patient.getEmail());
            email.setObjet("Prise de RDV");
            email.setContenu("Vous avez pris RDV avec le médecin "+medecin.getPrenom()+" "+medecin.getNom()+" le "+consultation.getCreneau().getDate()+" à "+consultation.getCreneau().getHeure()+". Vous recevrez un nouveau mail une fois que cette consultation sera confirmée par le médecin.");
            this.rabbitMQProducer.sendEmail(email);
            //----------------------
            return consultation;
        }else{
            throw new TypeConsultationInexistantException();
        }
    }
    @Override
    public void confirmerRDV(int idConsultation) throws ConsultationInexistanteException, ConsultationDejaConfirmeeException {
        if(consultationRepository.existsById(idConsultation)){
            Consultation consultation = consultationRepository.findConsultationById(idConsultation);
            if(!(consultation.estConfirme())){
                consultation.setConfirmation(true);
                consultationRepository.save(consultation);
                //-------RabbitMQ-------
                Patient patient = patientRepository.findPatientById(consultation.getIdPatient());
                EmailDTO email = new EmailDTO();
                email.setDestinataire(patient.getEmail());
                email.setObjet("Confirmation de RDV");
                email.setContenu("Votre consultation n°"+idConsultation+" a bien été confirmée !");
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
    public void modifierCRConsultation(int idConsultation, String compteRendu, Map<String,Integer> listeProduitsMedicaux) throws ConsultationInexistanteException, ConsultationNonConfirmeeException {
        if(consultationRepository.existsById(idConsultation)){
            Consultation consultation = consultationRepository.findConsultationById(idConsultation);
            if (!consultation.estConfirme()){
                throw new ConsultationNonConfirmeeException();
            }
            String ancienCompteRendu = consultation.getCompteRendu();
            consultation.setCompteRendu(compteRendu);
            consultation.setListeProduitsMedicaux(listeProduitsMedicaux);
            String nouveauCompteRendu = consultation.getCompteRendu();
            consultationRepository.save(consultation);
            //-------RabbitMQ-------
            Patient patient = patientRepository.findPatientById(consultation.getIdPatient());

            FactureDTO facture = new FactureDTO();
            facture.setPatient(patient);
            facture.setType(String.valueOf(consultation.getType()));
            facture.setListeProduits(consultation.getListeProduitsMedicaux());

            EmailDTO email = new EmailDTO();
            email.setDestinataire(patient.getEmail());

            if (consultation.getCompteRendu().equals("")){
                email.setObjet("Ajout d'un compte-rendu pour la consultation n°"+idConsultation);
                email.setContenu("Nouveau compte-rendu pour votre consultation : "+nouveauCompteRendu);
            }else{
                email.setObjet("Modification du compte-rendu pour la consultation n°"+idConsultation);
                email.setContenu("Des modifications ont été apportées au compte-rendu de votre consultation : \nAncien compte rendu : "+ ancienCompteRendu+"\n Nouveau compte-rendu : "+nouveauCompteRendu);
            }
            this.rabbitMQProducer.sendFacture(facture);
            this.rabbitMQProducer.sendEmail(email);
            //----------------------
        }else{
            throw new ConsultationInexistanteException();
        }
    }
    @Override
    public List<Consultation> voirConsultationsMedecin(int idMedecin) throws MedecinInexistantException, ConsultationInexistanteException, PasDeConsultationAssigneAuMedecinException {
        if(medecinRepository.existsById(idMedecin)){
            Medecin medecin = medecinRepository.findMedecinById(idMedecin);
            List<Consultation> reponse = new ArrayList<>();
            if (!medecin.getListeConsultations().isEmpty()){
                for (int idConsult: medecin.getListeConsultations()) {
                    if (consultationRepository.existsById(idConsult)){
                        reponse.add(consultationRepository.findConsultationById(idConsult));
                    }else{
                        throw new ConsultationInexistanteException();
                    }
                }
            }else{
                throw new PasDeConsultationAssigneAuMedecinException();
            }
            return reponse;
        }else{
            throw new MedecinInexistantException();
        }
    }
    @Override
    public void annulerConsultation(int idConsultation, int idPatient) throws ConsultationInexistanteException, PatientConnecteDifferentPatientConsultationException {
        if(consultationRepository.existsById(idConsultation)){
            Consultation consultation = consultationRepository.findConsultationById(idConsultation);
            Medecin medecin = medecinRepository.findMedecinById(consultation.getIdMedecin());
            Patient patient = patientRepository.findPatientById(consultation.getIdPatient());
            if (idPatient != patient.getId()){
                throw new PatientConnecteDifferentPatientConsultationException();
            }else{
                Creneau creneau = consultation.getCreneau();

                medecin.retirerConsultation(idConsultation);
                creneau.setDisponibilite(true);

                medecinRepository.save(medecin);
                creneauRepository.save(creneau);
                consultationRepository.removeConsultationById(idConsultation);

                //-------RabbitMQ-------
                EmailDTO email = new EmailDTO();
                email.setDestinataire(medecin.getEmail());
                email.setObjet("Annulation du RDV n°"+idConsultation);
                email.setContenu("La consultation n°"+idConsultation+" du patient "+ patient.getPrenom()+" "+patient.getNom()+" prévue le "+consultation.getCreneau().getDate()+" à "+consultation.getCreneau().getHeure()+" a été annulée.");
                this.rabbitMQProducer.sendEmail(email);
                //----------------------
            }
        }else{
            throw new ConsultationInexistanteException();
        }
    }
    @Override
    public Map<String,Integer> voirProduitsConsultation(int idConsultation) throws ConsultationInexistanteException {
        if (consultationRepository.existsById(idConsultation)){
            Consultation consultation = consultationRepository.findConsultationById(idConsultation);
            return consultation.getListeProduitsMedicaux();
        }else{
            throw new ConsultationInexistanteException();
        }

    }
    @Override
    public Collection<Patient> voirTousLesPatientsMedecin(int idMedecin) throws MedecinInexistantException, MedecinSansPatientException {
        if (medecinRepository.existsById(idMedecin)){
            Medecin medecin = medecinRepository.findMedecinById(idMedecin);
            Collection<Patient> res = new ArrayList<>();
            if (!medecin.getListePatients().isEmpty()){
                medecin.getListePatients().forEach(idPatient->
                        res.add(patientRepository.findPatientById(idPatient))
                );
                return res;
            }else{
                throw new MedecinSansPatientException();
            }
        }else{
            throw new MedecinInexistantException();
        }
    }
    @Override
    public Collection<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }
    @Override
    public Collection<Consultation> getAllConsultationsParType(String type) throws TypeConsultationInexistantException {
        if (Arrays.stream(TypeCons.values()).toList().contains(TypeCons.valueOf(type))){
            return consultationRepository.findAllConsultationsByType(TypeCons.valueOf(type));
        }else{
         throw new TypeConsultationInexistantException();
        }
    }
    @Override
    public Medecin getMedecinTraitant(String numSecu) throws PatientInexistantException, PasDeMedecinTraitantAssigneException {
        Patient patient =  patientRepository.findPatientByNumSecu(numSecu);
        if (patient==null){
            throw new PatientInexistantException();
        }
        Medecin medecin = medecinRepository.findMedecinById(patient.getIdMedecinTraitant());
        if (medecin==null) {
            throw new PasDeMedecinTraitantAssigneException();
        }
        return medecin;
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
    public Medecin getMedecinByID(int idMedecin) throws MedecinInexistantException {
        if(medecinRepository.existsById(idMedecin)){
            return medecinRepository.findMedecinById(idMedecin);
        }else{
            throw new MedecinInexistantException();
        }
    }
    @Override
    public Medecin getMedecinByEmail(String email) throws MedecinInexistantException {
        if (medecinRepository.existsByEmail(email)){
            return medecinRepository.findMedecinByEmail(email);
        }else{
            throw new MedecinInexistantException();
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
    @Override
    public Patient getPatientByEmail(String email) throws PatientInexistantException {
        if (patientRepository.existsByEmail(email)){
            return patientRepository.findPatientByEmail(email);
        }else{
            throw new PatientInexistantException();
        }
    }
}