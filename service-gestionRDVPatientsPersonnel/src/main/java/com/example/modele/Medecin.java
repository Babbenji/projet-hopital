package com.example.modele;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "medecin")
public class Medecin extends Utilisateur {
    private List<Consultation> listeconsultations_med;
    private List<Patient> listepatients_med;

    public Medecin(String prenom, String nom, String email) {
        super(prenom, nom, email);
    }

    public List<Consultation> getListeconsultations_med() {
        return listeconsultations_med;
    }

    public void setListeconsultations_med(List<Consultation> listeconsultations_med) {
        this.listeconsultations_med = listeconsultations_med;
    }

    public List<Patient> getListepatients_med() {
        return listepatients_med;
    }

    public void setListepatients_med(List<Patient> listepatients_med) {
        this.listepatients_med = listepatients_med;
    }

    public void ajouterConsultation(Consultation consultation){
        this.listeconsultations_med.add(consultation);
    }
    public void ajouterPatient(Patient patient){
        this.listepatients_med.add(patient);
    }

    public Medecin() {
    }
}
