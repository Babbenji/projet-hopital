package fr.univ.orleans.miage.serviceauthentification.modele;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String nom;
    private String prenom;
    private String motDePasse;

    public User(@JsonProperty("email") String email,
                @JsonProperty("nom") String nom,
                @JsonProperty("prenom") String prenom,
                @JsonProperty("motDePasse") String motDePasse) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.motDePasse = motDePasse;
    }

    public User() {}

    public User(String email, String mdp) {
        this.email = email;
        this.motDePasse = mdp;
    }

    public String getMotDePasse() {
        return motDePasse;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
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

    public boolean checkPassword(String password) {
        return this.motDePasse.equals(password);
    }

    public String toString() {
        return "User [email=" + email + ", nom=" + nom + ", prenom=" + prenom + ", motDePasse=" + motDePasse + "]";
    }

}
