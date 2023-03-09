package com.example.modele;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "medecin")
public class Medecin extends Utilisateur {
    private List<Consultation> listeConsultationsMed;
    private List<Patient> listePatientsMed;
    public Medecin() {}
    public Medecin(String prenom, String nom, String email) {
        super(prenom, nom, email);
    }
    public List<Consultation> getListeConsultationsMed() {
        return listeConsultationsMed;
    }
    public void setListeConsultationsMed(List<Consultation> listeConsultationsMed) {
        this.listeConsultationsMed = listeConsultationsMed;
    }
    public List<Patient> getListePatientsMed() {
        return listePatientsMed;
    }
    public void setListePatientsMed(List<Patient> listePatientsMed) {
        this.listePatientsMed = listePatientsMed;
    }
    public void ajouterConsultation(Consultation consultation){
        this.listeConsultationsMed.add(consultation);
    }
    public void ajouterPatient(Patient patient){
        this.listePatientsMed.add(patient);
    }

}
