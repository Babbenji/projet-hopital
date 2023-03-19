package fr.univ.servicegestionstockfournisseurs.modele;


import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;
@Document(collection = "Fournisseur")
public class Fournisseur {

    private int idFournisseur;
    private static int IDS = 1;
    private String nomFournisseur;
    private String adresseFournisseur;
    private String telephoneFournisseur;
    private Map<Integer,String> catalogueFournisseur;


    public Fournisseur(String nomFournisseur, String adresseFournisseur, String telephoneFournisseur) {
        this.nomFournisseur = nomFournisseur;
        this.adresseFournisseur = adresseFournisseur;
        this.telephoneFournisseur = telephoneFournisseur;
        this.catalogueFournisseur = new HashMap<>();
        this.idFournisseur = IDS++;
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

    public Map<Integer,String> getCatalogueFournisseur() {
        return catalogueFournisseur;
    }

    public void setCatalogueFournisseur(Map<Integer,String> catalogueFournisseur) {
        this.catalogueFournisseur = catalogueFournisseur;
    }

    public void deleteProduit(int idProduit) {
    	this.catalogueFournisseur.remove(idProduit);
    }
//    public void updateProduit(int idProduit, ProduitMedical produit) {
//    	this.catalogueFournisseur.replace(idProduit, produit);
//    }


    public String getAdresseFournisseur() {
        return adresseFournisseur;
    }

    public void setAdresseFournisseur(String adresseFournisseur) {
        this.adresseFournisseur = adresseFournisseur;
    }

    public String getTelephoneFournisseur() {
        return telephoneFournisseur;
    }

    public void setTelephoneFournisseur(String telephoneFournisseur) {
        this.telephoneFournisseur = telephoneFournisseur;
    }

    @Override
    public String toString() {
        return "Fournisseur{" +
                "nomFournisseur='" + nomFournisseur + '\'' +
                ", adresseFournisseur='" + adresseFournisseur + '\'' +
                ", telephoneFournisseur='" + telephoneFournisseur + '\'' +
                ", catalogueFournisseur=" + catalogueFournisseur +
                '}';
    }
}
