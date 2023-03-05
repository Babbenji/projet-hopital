package fr.univ.servicegestionstockfournisseurs.modele;



public class ProduitMedical {

    private int idProduitMedical;

    private float prixProduitMedical;

    private String nomProduitMedical;

    private String descriptionProduitMedical;

    public ProduitMedical(int idProduitMedical, float prixProduitMedical, String nomProduitMedical, String descriptionProduitMedical) {
        this.idProduitMedical = idProduitMedical;
        this.prixProduitMedical = prixProduitMedical;
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

    public float getPrixProduitMedical() {
        return prixProduitMedical;
    }

    public void setPrixProduitMedical(float prixProduitMedical) {
        this.prixProduitMedical = prixProduitMedical;
    }

    @Override
    public String toString() {
        return "ProduitMedical{" +
                "prixProduitMedical=" + prixProduitMedical +
                ", nomProduitMedical='" + nomProduitMedical + '\'' +
                ", descriptionProduitMedical='" + descriptionProduitMedical + '\'' +
                '}';
    }
}
