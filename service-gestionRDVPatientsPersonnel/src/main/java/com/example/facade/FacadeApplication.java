package com.example.facade;

import com.example.modele.Consultation;
import com.example.modele.Medecin;
import com.example.modele.Patient;

import java.util.List;

public interface FacadeApplication {


    /***
     * Ajouter un patient
     * */
    public Patient ajouterPatient(String prenom, String nom, String email, String numeroSecu, String numeroTel, String dateNaissance, String genre);
    /***
     * Ajouter un médecin
     * */
    public Medecin ajouterMedecin(String prenom, String nom, String email);

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

    //Prendre un RDV
    public Consultation prendreRDV(Patient patient, String dateRDV, String heureRDV, String motif, String ordonnance, String type);
    //Voir son dossier avec la liste des consultations et les données du patient (Pour le patient et pour les médecins/personnel)
    //Annuler un RDV (Côté médecin ou côté patient)

    //REQUETES
    public Patient getPatientByNumSecu(String numeroSecu);
    public Medecin getMedecinByPrenomNom(String prenomMedecin, String nomMedecin);
    public Medecin getMedecinByID(int idMedecin);
    public Consultation getConsultationByID(int idConsultation);
    public Medecin getMedecinTraitant(String numeroSecu);

}
