package fr.univ.servicegestionstockfournisseurs.modele.DTO;

public class ProduitMedicalDTO {
    private double prixProduitMedical;

    private String nomProduitMedical;

    private String descriptionProduitMedical;

    public double getPrixProduitMedical() {
        return prixProduitMedical;
    }

    public void setPrixProduitMedical(double prixProduitMedical) {
        this.prixProduitMedical = prixProduitMedical;
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

    @Override
    public String toString() {
        return "ProduitMedicalDTO{" +
                "prixProduitMedical=" + prixProduitMedical +
                ", nomProduitMedical='" + nomProduitMedical + '\'' +
                ", descriptionProduitMedical='" + descriptionProduitMedical + '\'' +
                '}';
    }
}
