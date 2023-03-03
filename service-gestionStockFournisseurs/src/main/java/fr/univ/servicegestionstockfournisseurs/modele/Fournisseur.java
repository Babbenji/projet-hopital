package fr.univ.servicegestionstockfournisseurs.modele;

import org.springframework.data.annotation.Id;

import javax.annotation.processing.Generated;
import java.util.List;

public class Fournisseur {

    @Id
    @Generated(value = "org.hibernate.id.IdentityGenerator")
    private int idFournisseur;
    private String nomFournisseur;
    private List<ProduitMedical> catalogueFournisseur;


    public Fournisseur(String nomFournisseur, List<ProduitMedical> catalogueFournisseur) {
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
