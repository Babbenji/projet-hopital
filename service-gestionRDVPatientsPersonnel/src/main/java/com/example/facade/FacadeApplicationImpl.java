package com.example.facade;

import com.example.modele.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
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
    public Patient ajouterPatient(String prenom, String nom, String email, String numeroSecu, String numeroTel, String dateNaissance, String genre) {
        Patient patient =  new Patient(prenom, nom, email, numeroSecu, numeroTel, Date.valueOf(dateNaissance), genre);
        patientRepository.save(patient);
        return patient;
    }
    @Override
    public void modifierAntecedentsPatient(String numeroSecu, String antecedents) {
        Patient pa = patientRepository.findByNumSecuPat(numeroSecu);
        pa.setAntecedentsPat(antecedents);
        patientRepository.save(pa);
    }
    @Override
    public void assignerMedecinTraitant(String numeroSecu, String prenomMedecin, String nomMedecin) {
        Patient p = patientRepository.findByNumSecuPat(numeroSecu);
        Medecin m = medecinRepository.findByPrenomUtiAndNomUti(prenomMedecin, nomMedecin);
        p.setMedecinTrPat(m);
        m.ajouterPatient(p);
        patientRepository.save(p);
        medecinRepository.save(m);
    }
    @Override
    public void confirmerRDV(int idConsultation) {
        Consultation consultation = consultationRepository.findConsultationByIdCons(idConsultation);
        consultation.setConfirmCons(true);
        consultationRepository.save(consultation);
    }
    @Override
    public List<Consultation> voirConsultationsMedecin(int idMedecin) {
        Medecin medecin = medecinRepository.findByIdUti(idMedecin);
        return medecin.getListeConsultationsMed();
    }
    @Override
    public void modifierCRConsultation(int idConsultation, String compteRendu) {
        Consultation consultation = consultationRepository.findConsultationByIdCons(idConsultation);
        consultation.setCompteRenduCons(compteRendu);
        consultationRepository.save(consultation);
    }
    @Override
    public void annulerConsultation(int idConsultation, String motif) {
        Consultation consultation = consultationRepository.findConsultationByIdCons(idConsultation);
        Creneau creneau = consultation.getCreneauCons();
        Medecin medecin = consultation.getMedecinCons();
        creneau.setDispoCren(true);
        consultationRepository.removeByIdCons(idConsultation);
        //Envoyer notif à Medecin(email+motif)
    }
    @Override
    public Consultation prendreRDV(Patient patient, String dateRDV, String heureRDV, String motif, String ordonnance, String type) {
        Creneau creneau = new Creneau(Date.valueOf(dateRDV), heureRDV);
        creneau.setDispoCren(false);
        creneauRepository.save(creneau);
        Medecin medecin = getMedecinTraitant(patient.getNumSecuPat());
        Consultation consultation = new Consultation(creneau, motif, TypeCons.valueOf(type), ordonnance, medecin, patient);
        medecin.ajouterConsultation(consultation);
        consultationRepository.save(consultation);
        medecinRepository.save(medecin);
        return consultation;
    }
    @Override
    public Medecin getMedecinTraitant(String numeroSecu) {
        Patient p = patientRepository.findByNumSecuPat(numeroSecu);
        return p.getMedecinTrPat();
    }
    @Override
    public void deleteConsultationByID(int idConsultation) {
        consultationRepository.removeByIdCons(idConsultation);
    }
    @Override
    public void deleteMedecinByID(int idMedecin) {
        medecinRepository.removeByIdUti(idMedecin);
    }
    @Override
    public void deletePatientByID(int idPatient) {
        patientRepository.removeByIdUti(idPatient);
    }
    @Override
    public Patient getPatientByEmail(String email) {
        return patientRepository.findPatientByEmailUti(email);
    }
}
