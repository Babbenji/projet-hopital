package fr.univ.servicegestionstockfournisseurs.modele.DTO;

import org.springframework.data.mongodb.core.mapping.Document;


public class PatientDTO
{
    private String prenom;
    private String nom;

    private String email;
    private String numSecu;
    private String numTel;
    private String dateNaissance;
    private String genre;
    private int idMedecinTraitant;
    private String antecedents;

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

    @Override
    public String toString() {
        return "PatientDTO{" +
                "prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", numSecu='" + numSecu + '\'' +
                ", numTel='" + numTel + '\'' +
                ", dateNaissance='" + dateNaissance + '\'' +
                ", genre='" + genre + '\'' +
                ", idMedecinTraitant=" + idMedecinTraitant +
                ", antecedents='" + antecedents + '\'' +
                '}';
    }
}