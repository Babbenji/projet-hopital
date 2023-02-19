package com.example.modele;
import java.util.List;
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

    public Medecin() {
    }
}
