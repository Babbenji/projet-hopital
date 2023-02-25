package com.example.modele;

import java.time.LocalDate;

public class Patient extends Utilisateur{
    private String numsecu_pat;
    private String numtel_pat;
    private LocalDate datenais_pat;
    private String genre_pat;
    private Medecin medecintr_pat;
    private String antecedents_pat;

    public Patient(String prenom, String nom, String email, String numeroSecu, String numeroTel, LocalDate dateNaissance, String genre) {
        super(nom, prenom, email);
        this.numsecu_pat = numeroSecu;
        this.numtel_pat = numeroTel;
        this.datenais_pat = dateNaissance;
        this.genre_pat = genre;
    }

    public String getNumsecu_pat() {
        return numsecu_pat;
    }

    public void setNumsecu_pat(String numsecu_pat) {
        this.numsecu_pat = numsecu_pat;
    }

    public String getNumtel_pat() {
        return numtel_pat;
    }

    public void setNumtel_pat(String numtel_pat) {
        this.numtel_pat = numtel_pat;
    }

    public LocalDate getDatenais_pat() {
        return datenais_pat;
    }

    public void setDatenais_pat(LocalDate datenais_pat) {
        this.datenais_pat = datenais_pat;
    }

    public String getGenre_pat() {
        return genre_pat;
    }

    public void setGenre_pat(String genre_pat) {
        this.genre_pat = genre_pat;
    }

    public Medecin getMedecintr_pat() {
        return medecintr_pat;
    }

    public void setMedecintr_pat(Medecin medecintr_pat) {
        this.medecintr_pat = medecintr_pat;
    }

    public String getAntecedents_pat() {
        return antecedents_pat;
    }

    public void setAntecedents_pat(String antecedents_pat) {
        this.antecedents_pat = antecedents_pat;
    }

    public Patient() {}
}
