package com.example.modele;

public class Utilisateur {
    private static int IDS = 1;
    private int id;
    private String prenom;
    private String nom;
    private String email;

    public Utilisateur(String prenom, String nom, String email) {
        this.id = IDS++;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
    }

    public Utilisateur() {

    }

    public int getId() {
        return id;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}