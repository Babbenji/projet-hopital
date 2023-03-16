package com.example.facade;

import com.example.exceptions.PatientInexistantException;
import com.example.modele.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    @Override
    public Medecin ajouterMedecin(String prenom, String nom, String email) {
        Medecin medecin =  new Medecin(prenom, nom, email);
        medecinRepository.save(medecin);
        return medecin;
    }
    @Override
    public Patient ajouterPatient(String prenom, String nom, String email, String numSecu, String numTel, String dateNaissance, String genre) {
        Patient patient =  new Patient(prenom, nom, email, numSecu, numTel, dateNaissance, genre);
        patientRepository.save(patient);
        return patient;
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
    public void assignerMedecinTraitant(String numSecu, String prenom, String nom) {
        Patient p = patientRepository.findPatientByNumSecu(numSecu);
        Medecin m = medecinRepository.findByPrenomAndNom(prenom, nom);
        p.setIdMedecinTraitant(m.getId());
        m.ajouterPatient(p);
        patientRepository.save(p);
        medecinRepository.save(m);
    }

    @Override
    public Consultation prendreRDV(Patient patient, String dateRDV, String heureRDV, String motif, String ordonnance, String type) {
        Creneau creneau = new Creneau(dateRDV, heureRDV);
        creneau.setDisponibilite(false);
        creneauRepository.save(creneau);
        Medecin medecin = getMedecinTraitant(patient.getNumSecu());
        Consultation consultation = new Consultation(creneau, motif, TypeCons.valueOf(type), ordonnance, medecin.getId(), patient.getId());
        medecin.ajouterConsultation(consultation);
        consultationRepository.save(consultation);
        medecinRepository.save(medecin);
        return consultation;
    }
    @Override
    public void confirmerRDV(int idConsultation) {
        Consultation consultation = consultationRepository.findConsultationById(idConsultation);
        consultation.setConfirmation(true);
        consultationRepository.save(consultation);
    }
    @Override
    public void modifierCRConsultation(int idConsultation, String compteRendu) {
        Consultation consultation = consultationRepository.findConsultationById(idConsultation);
        consultation.setCompteRendu(compteRendu);
        consultationRepository.save(consultation);
    }
    @Override
    public void annulerConsultation(int idConsultation) {
        Consultation consultation = consultationRepository.findConsultationById(idConsultation);
        Creneau creneau = consultation.getCreneau();
        Medecin medecin = medecinRepository.findById(consultation.getIdMedecin());
        creneau.setDisponibilite(true);
        consultationRepository.removeConsultationById(idConsultation);
        //Envoyer notif à Medecin(email)
    }
    @Override
    public List<Consultation> voirConsultationsMedecin(int idMedecin) {
        Medecin medecin = medecinRepository.findById(idMedecin);
        List<Consultation> reponse = new ArrayList<>();
        for (int idConsult: medecin.getListeConsultations()) {
            reponse.add(consultationRepository.findConsultationById(idConsult));
        }
        return reponse;
    }
    @Override
    public Medecin getMedecinTraitant(String numSecu) {
        return medecinRepository.findById(patientRepository.findPatientByNumSecu(numSecu).getIdMedecinTraitant());
    }
    @Override
    public void deleteConsultationByID(int idConsultation) {
        consultationRepository.removeConsultationById(idConsultation);
    }
    @Override
    public void deleteMedecinByID(int idMedecin) {
        medecinRepository.removeById(idMedecin);
    }
    @Override
    public void deletePatientByID(int idPatient) {
        patientRepository.removePatientById(idPatient);
    }
    @Override
    public Patient getPatientByEmail(String email) {
        return patientRepository.findPatientByEmail(email);
    }
    @Override
    public Patient getPatientByNumSecu(String numSecu) {
        return patientRepository.findPatientByNumSecu(numSecu);
    }
}
