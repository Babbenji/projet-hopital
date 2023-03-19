package fr.univ.servicegestionstockfournisseurs.modele;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "utilisateur")
public class Utilisateur {

    @Id
    private int idUtilisateur;
    private static int IDS = 1;
    private String nomUtilisateur;
    private String prenomUtilisateur;
    private String emailUtilisateur;
    private Map<Integer,Integer> panierUtilisateur; //idProduit et Quantité

    public Utilisateur(String nomUtilisateur, String prenomUtilisateur, String emailUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
        this.prenomUtilisateur = prenomUtilisateur;
        this.emailUtilisateur = emailUtilisateur;
        this.idUtilisateur = IDS++;
        this.panierUtilisateur = new HashMap<>();
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPrenomUtilisateur() {
        return prenomUtilisateur;
    }

    public void setPrenomUtilisateur(String prenomUtilisateur) {
        this.prenomUtilisateur = prenomUtilisateur;
    }

    public String getEmailUtilisateur() {
        return emailUtilisateur;
    }

    public void setEmailUtilisateur(String emailUtilisateur) {
        this.emailUtilisateur = emailUtilisateur;
    }

    public Map<Integer,Integer> getPanierUtilisateur() {
        return panierUtilisateur;
    }


}
