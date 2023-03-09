package com.example.modele;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "patient")
public class Patient extends Utilisateur{
    private String numSecuPat;
    private String numTelPat;
    private Date dateNaisPat;
    private String genrePat;
    private Medecin medecinTrPat;
    private String antecedentsPat;

    public Patient() {}
    public Patient(String prenom, String nom, String email, String numeroSecu, String numeroTel, Date dateNaissance, String genre) {
        super(nom, prenom, email);
        this.numSecuPat = numeroSecu;
        this.numTelPat = numeroTel;
        this.dateNaisPat = dateNaissance;
        this.genrePat = genre;
    }

    public String getNumSecuPat() {
        return numSecuPat;
    }
    public void setNumSecuPat(String numSecuPat) {
        this.numSecuPat = numSecuPat;
    }
    public String getNumTelPat() {
        return numTelPat;
    }
    public void setNumTelPat(String numTelPat) {
        this.numTelPat = numTelPat;
    }
    public Date getDateNaisPat() {
        return dateNaisPat;
    }
    public void setDateNaisPat(Date dateNaisPat) {
        this.dateNaisPat = dateNaisPat;
    }
    public String getGenrePat() {
        return genrePat;
    }
    public void setGenrePat(String genrePat) {
        this.genrePat = genrePat;
    }
    public Medecin getMedecinTrPat() {
        return medecinTrPat;
    }
    public void setMedecinTrPat(Medecin medecinTrPat) {
        this.medecinTrPat = medecinTrPat;
    }
    public String getAntecedentsPat() {
        return antecedentsPat;
    }
    public void setAntecedentsPat(String antecedentsPat) {
        this.antecedentsPat = antecedentsPat;
    }
}