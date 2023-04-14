package fr.univ.servicegestionstockfournisseurs.modele;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;

@Document(collection = "commande")
public class Commande {

    @Id
    private int idCommande;
    private static int IDS = 1;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateCommande;
    private double prixCommande;

    Map<String,Integer> produitsCommande;

    public Commande(Date dateCommande) {
        this.dateCommande = dateCommande;
        this.prixCommande = 0.0;
        this.idCommande = IDS++;
        this.produitsCommande = new HashMap<>();
    }

    public Map<String,Integer> getProduitsCommande() {
        return produitsCommande;
    }

    public void setProduitsCommande(Map<String,Integer> produitsCommande) {
        this.produitsCommande = produitsCommande;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }


    public double getPrixCommande() {
        return prixCommande;
    }

    public void setPrixCommande(double prixCommande) {
        this.prixCommande = prixCommande;
    }


}
