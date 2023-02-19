package com.example.modele;

public class Utilisateur {
    private int id_uti;
    private String prenom_uti;
    private String nom_uti;
    private String email_uti;

    public int getId_uti() {
        return id_uti;
    }

    public void setId_uti(int id_uti) {
        this.id_uti = id_uti;
    }

    public String getPrenom_uti() {
        return prenom_uti;
    }

    public void setPrenom_uti(String prenom_uti) {
        this.prenom_uti = prenom_uti;
    }

    public String getNom_uti() {
        return nom_uti;
    }

    public void setNom_uti(String nom_uti) {
        this.nom_uti = nom_uti;
    }

    public String getEmail_uti() {
        return email_uti;
    }

    public void setEmail_uti(String email_uti) {
        this.email_uti = email_uti;
    }

    public Utilisateur() {
    }

    public Utilisateur(String prenom_uti, String nom_uti, String email_uti) {
        this.prenom_uti = prenom_uti;
        this.nom_uti = nom_uti;
        this.email_uti = email_uti;
    }
}
