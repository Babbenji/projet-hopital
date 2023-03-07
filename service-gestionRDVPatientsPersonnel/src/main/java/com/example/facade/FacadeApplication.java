package com.example.facade;

import com.example.modele.Consultation;
import com.example.modele.Medecin;
import com.example.modele.Patient;

import java.util.List;

public interface FacadeApplication {


    /***
     * Ajouter un patient
     * */
    public String ajouterPatient(String prenom, String nom, String email, String numeroSecu, String numeroTel, String dateNaissance, String genre);
    /***
     * Ajouter un médecin
     * */
    public int ajouterMedecin(String prenom, String nom, String email);

    public void modifierAntecedentsPatient(String numeroSecu, String antecedents);
    //Assigner un medecin à un patient
    public void assignerMedecinTraitant(String numeroSecu, String nomMedecin, String prenomMedecin);
    //Confirmer un RDV
    public void confirmerRDV(int idConsultation);

    //Modifier un patient
    //Supprimer un patient
    //Modifier un médecin
    //Supprimer un médecin

    //Voir toutes les consultations
    public List<Consultation> voirConsultationsMedecin(int idMedecin);
    //Rediger le CR d'une consultation
    public void modifierCRConsultation(int idConsultation, String compteRendu);
    //Annuler un RDV (Côté médecin)
    public void annulerConsultation(int idConsultation);

    //Prendre un RDV
    public Consultation prendreRDV(Patient patient, String dateRDV, String heureRDV, String motif, String ordonnance, String type);
    //Voir son dossier avec la liste des consultations et les données du patient (Pour le patient et pour les médecins/personnel)
    //Demande d'annulation par le patient
    public void demanderAnnulation(int idConsultation, String motifAnnulation);

    //REQUETES

    public Medecin getMedecinTraitant(String numeroSecu);
    public void deleteConsultationByID(int idConsultation);
    public void deleteMedecinByID(int idMedecin);
    public void deletePatientByID(int idPatient);

    public Patient getPatientByEmail(String email);


}
