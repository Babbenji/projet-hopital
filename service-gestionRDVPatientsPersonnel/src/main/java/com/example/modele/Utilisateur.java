package com.example.modele;

public class Utilisateur {
    private static int IDS = 1;
    private int idUti;
    private String prenomUti;
    private String nomUti;
    private String emailUti;

    public Utilisateur() {}
    public Utilisateur(String prenomUti, String nomUti, String emailUti) {
        this.idUti = IDS++;
        this.prenomUti = prenomUti;
        this.nomUti = nomUti;
        this.emailUti = emailUti;
    }

    public int getIdUti() {
        return idUti;
    }
    public void setIdUti(int idUti) {
        this.idUti = idUti;
    }
    public String getPrenomUti() {
        return prenomUti;
    }
    public void setPrenomUti(String prenomUti) {
        this.prenomUti = prenomUti;
    }
    public String getNomUti() {
        return nomUti;
    }
    public void setNomUti(String nomUti) {
        this.nomUti = nomUti;
    }
    public String getEmailUti() {
        return emailUti;
    }
    public void setEmailUti(String emailUti) {
        this.emailUti = emailUti;
    }
}