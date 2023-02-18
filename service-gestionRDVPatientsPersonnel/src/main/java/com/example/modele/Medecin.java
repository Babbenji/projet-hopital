package com.example.modele;
import java.util.List;
public class Medecin extends Utilisateur {
    private List<Creneau> listecreneaux_med;
    private List<Patient> listepatients_med;

    public List<Creneau> getListecreneaux_med() {
        return listecreneaux_med;
    }

    public void setListecreneaux_med(List<Creneau> listecreneaux_med) {
        this.listecreneaux_med = listecreneaux_med;
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
