package com.example.modele;

import java.time.LocalDate;

public class Creneau {
    private LocalDate date_cren;
    private String heure_cren;
    private boolean dispo_cren;

    public Creneau(LocalDate date_cren, String heure_cren) {
        this.date_cren = date_cren;
        this.heure_cren = heure_cren;
        this.dispo_cren=true;
    }

    public LocalDate getDate_cren() {
        return date_cren;
    }

    public void setDate_cren(LocalDate date_cren) {
        this.date_cren = date_cren;
    }

    public String getHeure_cren() {
        return heure_cren;
    }

    public void setHeure_cren(String heure_cren) {
        this.heure_cren = heure_cren;
    }

    public boolean isDispo_cren() {
        return dispo_cren;
    }

    public void setDispo_cren(boolean dispo_cren) {
        this.dispo_cren = dispo_cren;
    }

    public Creneau() {
    }
}
