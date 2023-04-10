package com.example.facade;

import com.example.exceptions.*;
import com.example.modele.Consultation;
import com.example.modele.Medecin;
import com.example.modele.Patient;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface FacadeApplication {

    Medecin ajouterMedecin(String prenom, String nom, String email) throws AdresseMailDejaUtiliseeException;
    Patient ajouterPatient(String prenom, String nom, String email, String numSecu, String numTel, String dateNaissance, String genre) throws NumeroSecuDejaAttribueException, AdresseMailDejaUtiliseeException;
    void modifierAntecedentsPatient(String numSecu, String antecedents) throws PatientInexistantException;
    void assignerMedecinTraitant(String numSecu, String nomMedecin, String prenomMedecin) throws PatientInexistantException, MedecinInexistantException, PatientDejaAttribueAuMedecinException;
    void confirmerRDV(int idConsultation) throws ConsultationInexistanteException, ConsultationDejaConfirmeeException;
    List<Consultation> voirConsultationsMedecin(int idMedecin) throws MedecinInexistantException, ConsultationInexistanteException, PasDeConsultationAssigneAuMedecinException;
    void modifierCRConsultation(int idConsultation, String compteRendu, Map<String,Integer> listeProduitsMedicaux) throws ConsultationInexistanteException, ConsultationNonConfirmeeException;
    void annulerConsultation(int idConsultation, int idPatient) throws ConsultationInexistanteException, PatientConnecteDifferentPatientConsultationException;
    Consultation prendreRDV(Patient patient, String dateRDV, String heureRDV, String motif, String type) throws TypeConsultationInexistantException, CreneauIndisponibleException, PasDeMedecinTraitantAssigneException, PatientInexistantException;

    Map<String,Integer> voirProduitsConsultation(int idConsultation) throws ConsultationInexistanteException;
    Collection<Patient> voirTousLesPatientsMedecin(int idMedecin) throws MedecinInexistantException, MedecinSansPatientException;
    Collection<Consultation> getAllConsultations();
    Collection<Consultation> getAllConsultationsParType(String type) throws TypeConsultationInexistantException;

    //Afficher un medecin
    //Modifier un patient
    //Modifier un médecin

    //Voir les consultations d'un patient
    //Voir son dossier avec la liste des consultations et les données du patient (Pour le patient et pour les médecins/personnel)
    //Modifier la date d'un RDV ?

    //REQUETES
    Medecin getMedecinTraitant(String numSecu) throws PatientInexistantException, PasDeMedecinTraitantAssigneException;

    void deleteConsultationByID(int idConsultation) throws ConsultationInexistanteException;
    void deleteMedecinByID(int idMedecin) throws MedecinInexistantException;
    void deletePatientByID(int idPatient) throws PatientInexistantException;

    Medecin getMedecinByID(int idMedecin) throws MedecinInexistantException;
    Medecin getMedecinByEmail(String email) throws MedecinInexistantException;
    Patient getPatientByNumSecu(String numSecu) throws PatientInexistantException;
    Patient getPatientByEmail(String email) throws PatientInexistantException;


}
