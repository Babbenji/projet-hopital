package fr.univ.servicegestionstockfournisseurs.modele;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
@Document(collection = "panier")

public class Panier {
    private int idPanier;
    private static int IDS = 1;
    private Map<ProduitMedical, Integer> panierProduits; // idProduit / quantité

    public Panier( Map<ProduitMedical, Integer> panierProduits) {
        this.panierProduits = panierProduits;
        this.idPanier = IDS++;
    }

    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    public Map<ProduitMedical, Integer> getPanierProduits() {
        return panierProduits;
    }

    public void setPanierProduits(Map<ProduitMedical, Integer> panierProduits) {
        this.panierProduits = panierProduits;
    }
}
