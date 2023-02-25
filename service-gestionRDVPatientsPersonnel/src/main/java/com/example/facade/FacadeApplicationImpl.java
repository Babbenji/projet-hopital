package com.example.facade;

import com.example.modele.*;

import java.time.LocalDate;
import java.util.List;

public class FacadeApplicationImpl implements FacadeApplication{
    @Override
    public Patient ajouterPatient(String prenom, String nom, String email, String numeroSecu, String numeroTel, String dateNaissance, String genre) {
        return new Patient(prenom, nom, email, numeroSecu, numeroTel, LocalDate.parse(dateNaissance), genre);
    }

    @Override
    public Medecin ajouterMedecin(String prenom, String nom, String email) {
        return new Medecin(prenom, nom, email);
    }

    @Override
    public void modifierAntecedentsPatient(String numeroSecu, String antecedents) {
        Patient p = this.getPatientByNumSecu(numeroSecu);
        p.setAntecedents_pat(antecedents);
    }

    @Override
    public void assignerMedecinTraitant(String numeroSecu, String prenomMedecin, String nomMedecin) {
        Patient p = this.getPatientByNumSecu(numeroSecu);
        Medecin m = this.getMedecinByPrenomNom(prenomMedecin, nomMedecin);
        p.setMedecintr_pat(m);
    }

    @Override
    public void confirmerRDV(int idConsultation) {
        Consultation consultation = getConsultationByID(idConsultation);
        consultation.setConfirm_cons(true);
    }

    @Override
    public List<Consultation> voirConsultationsMedecin(int idMedecin) {
        Medecin medecin = getMedecinByID(idMedecin);
        return medecin.getListeconsultations_med();
    }

    @Override
    public void modifierCRConsultation(int idConsultation, String compteRendu) {
        Consultation consultation = getConsultationByID(idConsultation);
        consultation.setCr_cons(compteRendu);
    }

    @Override
    public void annulerConsultation(int idConsultation) {
        //Rendre dispo creneau à nouveau
        //Supprimer la consultation
    }

    @Override
    public Consultation prendreRDV(Patient patient, String dateRDV, String heureRDV, String motif, String ordonnance, String type) {
        Creneau creneau = new Creneau(LocalDate.parse(dateRDV), heureRDV);
        Medecin medecin = getMedecinTraitant(patient.getNumsecu_pat());
        creneau.setDispo_cren(false);
        Consultation consultation = new Consultation(creneau, motif, ordonnance, medecin, patient, TypeCons.valueOf(type));
        return consultation;
    }

    @Override
    public void demanderAnnulation(int idConsultation, String motifAnnulation) {
        //Generer notif avec message
    }

    @Override
    public Patient getPatientByNumSecu(String numeroSecu) {
        return null;
    }

    @Override
    public Medecin getMedecinByPrenomNom(String prenomMedecin, String nomMedecin) {
        return null;
    }

    @Override
    public Medecin getMedecinByID(int idMedecin) {
        return null;
    }

    @Override
    public Consultation getConsultationByID(int idConsultation) {
        return null;
    }

    @Override
    public Medecin getMedecinTraitant(String numeroSecu) {
        Patient p = getPatientByNumSecu(numeroSecu);
        return p.getMedecintr_pat();
    }

    @Override
    public void deleteConsultationByID(int idConsultation) {

    }
    @Override
    public void deleteMedecinByID(int idMedecin) {

    }
    @Override
    public void deletePatientByID(int idPatient) {

    }
}
