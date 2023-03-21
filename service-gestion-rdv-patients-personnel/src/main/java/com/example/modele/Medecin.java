package com.example.modele;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Document(collection = "medecin")
public class Medecin extends Utilisateur {
    private List<Integer> listeConsultations;
    private List<Integer> listePatients;
    public Medecin(String prenom, String nom, String email) {
        super(prenom, nom, email);
        this.listeConsultations = new ArrayList<>();
        this.listePatients = new ArrayList<>();
    }
    public List<Integer> getListeConsultations() {
        return listeConsultations;
    }
    public List<Integer> getListePatients() {
        return listePatients;
    }
    public void ajouterConsultation(Consultation consultation){
        this.listeConsultations.add(consultation.getId());
    }
    public void ajouterPatient(Patient patient){
        this.listePatients.add(patient.getId());
    }

    public void retirerConsultation(int idConsultation){
        this.listeConsultations.remove(this.listeConsultations.indexOf(idConsultation));
    }
    //Retirer un patient
}
