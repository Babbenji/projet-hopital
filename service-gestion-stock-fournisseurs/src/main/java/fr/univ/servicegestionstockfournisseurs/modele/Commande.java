package fr.univ.servicegestionstockfournisseurs.modele;

import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.annotation.processing.Generated;
import java.util.Date;
import java.util.Map;

@Document(collection = "Commande")
public class Commande {

//    @Id
//    @Generated(value = "org.hibernate.id.IdentityGenerator")
    private int idCommande;
    private static int IDS = 1;
    private Date dateCommande;
    private Double prixCommande;

    public Commande( Date dateCommande) {
        this.dateCommande = dateCommande;
        this.idCommande = IDS++;
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


    public Double getPrixCommande() {
        return prixCommande;
    }

    public void setPrixCommande(Double prixCommande) {
        this.prixCommande = prixCommande;
    }

    @Override
    public String toString() {
        return "Commande{" +
                ", dateCommande=" + dateCommande +
                ", prixCommande=" + prixCommande +
                '}';
    }
}
