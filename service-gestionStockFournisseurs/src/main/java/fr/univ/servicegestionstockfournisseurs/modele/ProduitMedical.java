package fr.univ.servicegestionstockfournisseurs.modele;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "produitMedical")
public class ProduitMedical {

    private int idProduitMedical;
    private static int IDS = 1;

    private Double prixProduitMedical;

    private String nomProduitMedical;

    private String descriptionProduitMedical;

    private int stockProduitMedical;



    public ProduitMedical( Double prixProduitMedical, String nomProduitMedical, String descriptionProduitMedical, int stockProduitMedical) {
        this.idProduitMedical = IDS++;
        this.prixProduitMedical = prixProduitMedical;
        this.nomProduitMedical = nomProduitMedical;
        this.descriptionProduitMedical = descriptionProduitMedical;
        this.stockProduitMedical = stockProduitMedical;

    }

    public int getStockProduitMedical() {
        return stockProduitMedical;
    }

    public void setStockProduitMedical(int stockProduitMedical) {
        this.stockProduitMedical = stockProduitMedical;
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

    public Double getPrixProduitMedical() {
        return prixProduitMedical;
    }

    public void setPrixProduitMedical(Double prixProduitMedical) {
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
