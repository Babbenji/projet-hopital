package fr.univ.servicegestionstockfournisseurs.modele.DTO;

public class FournisseurDTO {
    private String nomFournisseur;
    private String adresseFournisseur;
    private String telephoneFournisseur;

    public String getNomFournisseur() {
        return nomFournisseur;
    }

    public void setNomFournisseur(String nomFournisseur) {
        this.nomFournisseur = nomFournisseur;
    }

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
        return "FournisseurDTO{" +
                "nomFournisseur='" + nomFournisseur + '\'' +
                ", adresseFournisseur='" + adresseFournisseur + '\'' +
                ", telephoneFournisseur='" + telephoneFournisseur + '\'' +
                '}';
    }

}
