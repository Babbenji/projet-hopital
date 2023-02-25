package com.example.facade;

import com.example.modele.*;
import com.example.repository.ConsultationRepository;
import com.example.repository.CreneauRepository;
import com.example.repository.MedecinRepository;
import com.example.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
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
    public int ajouterPatient(String prenom, String nom, String email, String numeroSecu, String numeroTel, String dateNaissance, String genre) {
        Patient patient =  new Patient(prenom, nom, email, numeroSecu, numeroTel, LocalDate.parse(dateNaissance), genre);
        patientRepository.save(patient);

        return patient.getId_uti();

    }

    @Override
    public int ajouterMedecin(String prenom, String nom, String email) {

        Medecin medecin =  new Medecin(prenom, nom, email);
        medecinRepository.save(medecin);
        return medecin.getId_uti();
    }

    @Override
    public void modifierAntecedentsPatient(String numeroSecu, String antecedents) {
        Patient pa = patientRepository.findByNumsecu_pat(numeroSecu);
        pa.setAntecedents_pat(antecedents);
        patientRepository.save(pa);
    }

    @Override
    public void assignerMedecinTraitant(String numeroSecu, String prenomMedecin, String nomMedecin) {
        Patient p = patientRepository.findByNumsecu_pat(numeroSecu);
        Medecin m = medecinRepository.findByPrenom_utiAndNom_uti(prenomMedecin, nomMedecin);
        p.setMedecintr_pat(m);
        patientRepository.save(p);

    }

    @Override
    public void confirmerRDV(int idConsultation) {
        Consultation consultation = consultationRepository.findConsultationById_cons(idConsultation);
        consultation.setConfirm_cons(true);
        consultationRepository.save(consultation);
    }

    @Override
    public List<Consultation> voirConsultationsMedecin(int idMedecin) {
        Medecin medecin = medecinRepository.findById_uti(idMedecin);
        return medecin.getListeconsultations_med();
    }

    @Override
    public void modifierCRConsultation(int idConsultation, String compteRendu) {
        Consultation consultation = consultationRepository.findConsultationById_cons(idConsultation);

        consultation.setCr_cons(compteRendu);
        consultationRepository.save(consultation);
    }
    @Override
    public void annulerConsultation(int idConsultation) {
        //Rendre dispo creneau à nouveau
        //Supprimer la consultation
    }


//Si le creneau existe déjà, erreur : a faire
    @Override
    public Consultation prendreRDV(Patient patient, String dateRDV, String heureRDV, String motif, String ordonnance, String type) {
        Creneau creneau = new Creneau(LocalDate.parse(dateRDV), heureRDV);
        creneau.setDispo_cren(false);
        creneauRepository.save(creneau);

        Medecin medecin = getMedecinTraitant(patient.getNumsecu_pat());

        Consultation consultation = new Consultation(creneau, motif, ordonnance, medecin, patient, TypeCons.valueOf(type));
        consultationRepository.save( consultation);
        return consultation;
    }

    @Override
    public void demanderAnnulation(int idConsultation, String motifAnnulation) {
        //Generer notif avec message
    }



    @Override
    public Medecin getMedecinTraitant(String numeroSecu) {
        Patient p = patientRepository.findByNumsecu_pat(numeroSecu);
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
