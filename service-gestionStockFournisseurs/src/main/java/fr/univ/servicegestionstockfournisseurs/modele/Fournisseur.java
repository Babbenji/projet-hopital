package fr.univ.servicegestionstockfournisseurs.modele;

import java.util.List;

public class Fournisseur {

    private int idFournisseur;

    private String nomFournisseur;

    private List<ProduitMedical> catalogueFournisseur;


    public Fournisseur(int idFournisseur, String nomFournisseur, List<ProduitMedical> catalogueFournisseur) {
        this.idFournisseur = idFournisseur;
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

    public List<ProduitMedical> getCatalogueFournisseur() {
        return catalogueFournisseur;
    }

    public void setCatalogueFournisseur(List<ProduitMedical> catalogueFournisseur) {
        this.catalogueFournisseur = catalogueFournisseur;
    }
}
