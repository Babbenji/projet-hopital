package fr.univ.servicegestionstockfournisseurs.modele;

public class ProduitMedical {

    private int idProduitMedical;

    private String nomProduitMedical;

    private String descriptionProduitMedical;

    public ProduitMedical(int idProduitMedical, String nomProduitMedical, String descriptionProduitMedical) {
        this.idProduitMedical = idProduitMedical;
        this.nomProduitMedical = nomProduitMedical;
        this.descriptionProduitMedical = descriptionProduitMedical;
    }


    public int getIdProduitMedical() {
        return idProduitMedical;
    }

    public void setIdProduitMedical(int idProduitMedical) {
        this.idProduitMedical = idProduitMedical;
    }

    public String getNomProduitMedical() {
        return nomProduitMedical;
    }

    public void setNomProduitMedical(String nomProduitMedical) {
        this.nomProduitMedical = nomProduitMedical;
    }

    public String getDescriptionProduitMedical() {
        return descriptionProduitMedical;
    }

    public void setDescriptionProduitMedical(String descriptionProduitMedical) {
        this.descriptionProduitMedical = descriptionProduitMedical;
    }
}
