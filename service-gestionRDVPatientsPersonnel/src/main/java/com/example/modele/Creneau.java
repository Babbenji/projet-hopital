package com.example.modele;

import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "creneau")
public class Creneau {
    private String date;
    private String heure;
    private boolean disponibilite;
    public Creneau(String date, String heure) {
        this.date = date;
        this.heure = heure;
        this.disponibilite =true;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getHeure() {
        return heure;
    }
    public void setHeure(String heure) {
        this.heure = heure;
    }
    public boolean estDispo() {
        return disponibilite;
    }
    public void setDisponibilite(boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    @Override
    public String toString() {
        return "Creneau{" +
                "date='" + date + '\'' +
                ", heure='" + heure + '\'' +
                ", disponibilite=" + disponibilite +
                '}';
    }
}
