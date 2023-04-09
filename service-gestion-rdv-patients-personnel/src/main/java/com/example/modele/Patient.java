package com.example.modele;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "patient")
public class Patient extends Utilisateur{
    private String numSecu;
    private String numTel;
    private String dateNaissance;
    private String genre;
    private int idMedecinTraitant;
    private String antecedents;
    public Patient(String prenom, String nom, String email, String numSecu, String numTel, String dateNaissance, String genre) {
        super(prenom, nom, email);
        this.numSecu = numSecu;
        this.numTel = numTel;
        this.dateNaissance = dateNaissance;
        this.genre = genre;
    }
    public String getNumSecu() {
        return numSecu;
    }
    public void setNumSecu(String numSecu) {
        this.numSecu = numSecu;
    }
    public String getNumTel() {
        return numTel;
    }
    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }
    public String getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public int getIdMedecinTraitant() {
        return idMedecinTraitant;
    }
    public void setIdMedecinTraitant(int idMedecinTraitant) {
        this.idMedecinTraitant = idMedecinTraitant;
    }
    public String getAntecedents() {
        return antecedents;
    }
    public void setAntecedents(String antecedents) {
        this.antecedents = antecedents;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "nom='" + this.getNom() + '\'' +
                ", prenom='" + this.getPrenom() + '\'' +
                ", email='" + this.getEmail()+ '\'' +
                ", numSecu='" + numSecu + '\'' +
                ", numTel='" + numTel + '\'' +
                ", dateNaissance='" + dateNaissance + '\'' +
                ", genre='" + genre + '\'' +
                ", idMedecinTraitant=" + idMedecinTraitant +
                ", antecedents='" + antecedents + '\'' +
                '}';
    }
}