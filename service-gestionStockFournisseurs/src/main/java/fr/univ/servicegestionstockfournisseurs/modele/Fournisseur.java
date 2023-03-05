package fr.univ.servicegestionstockfournisseurs.modele;

import org.springframework.data.annotation.Id;

import javax.annotation.processing.Generated;
import java.util.List;
import java.util.Map;

public class Fournisseur {

    @Id
    @Generated(value = "org.hibernate.id.IdentityGenerator")
    private int idFournisseur;
    private String nomFournisseur;
    private Map<Integer,ProduitMedical> catalogueFournisseur;


    public Fournisseur(String nomFournisseur, Map<Integer,ProduitMedical> catalogueFournisseur) {
        this.nomFournisseur = nomFournisseur;
        this.catalogueFournisseur = catalogueFournisseur;
    }

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public String getNomFournisseur() {
        return nomFournisseur;
    }

    public void setNomFournisseur(String nomFournisseur) {
        this.nomFournisseur = nomFournisseur;
    }

    public Map<Integer,ProduitMedical> getCatalogueFournisseur() {
        return catalogueFournisseur;
    }

    public void setCatalogueFournisseur(Map<Integer,ProduitMedical> catalogueFournisseur) {
        this.catalogueFournisseur = catalogueFournisseur;
    }

    public void deleteProduit(int idProduit) {
    	this.catalogueFournisseur.remove(idProduit);
    }
    public void updateProduit(int idProduit, ProduitMedical produit) {
    	this.catalogueFournisseur.replace(idProduit, produit);
    }

    @Override
    public String toString() {
        return "Fournisseur{" +
                "catalogueFournisseur=" + catalogueFournisseur.toString() +
                '}';
    }
}
