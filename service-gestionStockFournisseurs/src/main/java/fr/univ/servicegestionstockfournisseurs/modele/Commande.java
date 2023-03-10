package fr.univ.servicegestionstockfournisseurs.modele;

import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.processing.Generated;
import java.util.Date;
import java.util.Map;

@Document(collection = "commande")

public class Commande {

    @Id
    @Generated(value = "org.hibernate.id.IdentityGenerator")
    private int idCommande;
    private Map<Integer,Integer> panierCommande;
    private Date dateCommande;
    private Double prixCommande;

    public Commande(Map<Integer,Integer> panierCommande, Date dateCommande) {
        this.panierCommande = panierCommande;
        this.dateCommande = dateCommande;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public Map<Integer, Integer> getPanierCommande() {
        return panierCommande;
    }

    public void setPanierCommande(Map<Integer, Integer> panierCommande) {
        this.panierCommande = panierCommande;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Double getPrixCommande() {
        return prixCommande;
    }

    public void setPrixCommande(Double prixCommande) {
        this.prixCommande = prixCommande;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "panierCommande=" + panierCommande.toString() +
                ", dateCommande=" + dateCommande +
                ", prixCommande=" + prixCommande +
                '}';
    }
}
