package com.example.facade;

import com.example.exceptions.PatientInexistantException;
import com.example.modele.Consultation;
import com.example.modele.Medecin;
import com.example.modele.Patient;
import java.util.List;

public interface FacadeApplication {

    Medecin ajouterMedecin(String prenom, String nom, String email);
    Patient ajouterPatient(String prenom, String nom, String email, String numSecu, String numTel, String dateNaissance, String genre);

    //Afficher un patient
    //Modifier un patient
    //Supprimer un patient
    //Afficher un medecin
    //Modifier un médecin
    //Supprimer un médecin

    void modifierAntecedentsPatient(String numSecu, String antecedents) throws PatientInexistantException;
    void assignerMedecinTraitant(String numSecu, String nomMedecin, String prenomMedecin);
    void confirmerRDV(int idConsultation);
    List<Consultation> voirConsultationsMedecin(int idMedecin);
    void modifierCRConsultation(int idConsultation, String compteRendu);
    void annulerConsultation(int idConsultation);
    Consultation prendreRDV(Patient patient, String dateRDV, String heureRDV, String motif, String ordonnance, String type);

    //Voir son dossier avec la liste des consultations et les données du patient (Pour le patient et pour les médecins/personnel)
    //Voir toutes les consultations
    //Modifier la date d'un RDV
    //Liste de Médicaments utilisés dans un RDV
    //Diminuer la quantité d'un produit médicale dans une consultation
    //Liste des patients d'un medecin

    //REQUETES

    Medecin getMedecinTraitant(String numSecu);
    void deleteConsultationByID(int idConsultation);
    void deleteMedecinByID(int idMedecin);
    void deletePatientByID(int idPatient);
    Patient getPatientByEmail(String email);
    Patient getPatientByNumSecu(String numSecu);


}
