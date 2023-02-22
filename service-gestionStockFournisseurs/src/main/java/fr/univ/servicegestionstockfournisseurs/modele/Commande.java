package fr.univ.servicegestionstockfournisseurs.modele;

import java.util.Date;
import java.util.Map;

public class Commande {

    private int idCommande;
    private Map<ProduitMedical,Integer> panierCommande;
    private Date dateCommande;
    private float prixCommande;

    public Commande(Map<ProduitMedical,Integer> panierCommande, Date dateCommande) {
        this.panierCommande = panierCommande;
        this.dateCommande = dateCommande;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public Map<ProduitMedical, Integer> getPanierCommande() {
        return panierCommande;
    }

    public void setPanierCommande(Map<ProduitMedical, Integer> panierCommande) {
        this.panierCommande = panierCommande;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public float getPrixCommande() {
        return prixCommande;
    }

    public void setPrixCommande(float prixCommande) {
        this.prixCommande = prixCommande;
    }
}
