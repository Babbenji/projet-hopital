package com.example.facade;

import com.example.exceptions.*;
import com.example.modele.Consultation;
import com.example.modele.Medecin;
import com.example.modele.Patient;
import java.util.List;

public interface FacadeApplication {

    Medecin ajouterMedecin(String prenom, String nom, String email) throws AdresseMailDejaUtiliseeException;
    Patient ajouterPatient(String prenom, String nom, String email, String numSecu, String numTel, String dateNaissance, String genre) throws NumeroSecuDejaAttribueException;
    void modifierAntecedentsPatient(String numSecu, String antecedents) throws PatientInexistantException;
    void assignerMedecinTraitant(String numSecu, String nomMedecin, String prenomMedecin) throws PatientInexistantException, MedecinInexistantException;
    void confirmerRDV(int idConsultation) throws ConsultationInexistanteException, ConsultationDejaConfirmeeException;
    List<Consultation> voirConsultationsMedecin(int idMedecin) throws MedecinInexistantException, ConsultationInexistanteException;
    void modifierCRConsultation(int idConsultation, String compteRendu) throws ConsultationInexistanteException;
    void annulerConsultation(int idConsultation) throws MedecinInexistantException, ConsultationInexistanteException;
    Consultation prendreRDV(Patient patient, String dateRDV, String heureRDV, String motif, String ordonnance, String type) throws TypeConsultationInexistantException, CreneauIndisponibleException, PasDeMedecinTraitantAssigneException;

    //Afficher un patient
    //Modifier un patient
    //Afficher un medecin
    //Modifier un médecin
    //Voir son dossier avec la liste des consultations et les données du patient (Pour le patient et pour les médecins/personnel)
    //Voir toutes les consultations
    //Modifier la date d'un RDV
    //Liste de Médicaments utilisés dans un RDV
    //Diminuer la quantité d'un produit médicale dans une consultation
    //Liste des patients d'un medecin
    //Voir toutes les consultations par type

    //REQUETES
    Medecin getMedecinTraitant(String numSecu) throws PatientInexistantException, MedecinInexistantException;
    void deleteConsultationByID(int idConsultation) throws ConsultationInexistanteException;
    void deleteMedecinByID(int idMedecin) throws MedecinInexistantException;
    void deletePatientByID(int idPatient) throws PatientInexistantException;
    Patient getPatientByEmail(String email) throws PatientInexistantException;
    Patient getPatientByNumSecu(String numSecu) throws PatientInexistantException;


}
