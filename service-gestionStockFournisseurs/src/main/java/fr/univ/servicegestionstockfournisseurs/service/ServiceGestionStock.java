package fr.univ.servicegestionstockfournisseurs.service;

import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import fr.univ.servicegestionstockfournisseurs.modele.ProduitMedical;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.*;
import org.springframework.stereotype.Component;

@Component("serviceGestionStock")
public class ServiceGestionStock implements FacadeServiceGestionStock {

        @Override
        public void passerCommande(Commande commande)
        {

        }

        @Override
        public void ajouterFournisseur(Fournisseur fournisseur) throws FournisseurDejaExistantException {

        }

        @Override
        public void ajouterProduitPanier(ProduitMedical produit) throws ProduitDejaExistantException {
            // TODO
        }

        @Override
        public void annulerCommande(Commande commande) throws CommandeInexistanteException {
            // TODO
        }

        @Override
        public void supprimerFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException {
            // TODO
        }

        @Override
        public void supprimerProduit(ProduitMedical produit) throws ProduitInexistantException {
            // TODO
        }

        @Override
        public void modifierCommande(Commande commande) throws CommandeInexistanteException {
            // TODO
        }

        @Override
        public void modifierFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException {
            // TODO
        }

        @Override
        public void modifierProduit(ProduitMedical produit) throws ProduitInexistantException {
            // TODO
        }

        @Override
        public void afficherCommande(Commande commande) throws CommandeInexistanteException{
            // TODO
        }

        @Override
        public void afficherFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException {
            // TODO
        }

        @Override
        public void afficherProduit(ProduitMedical produit) throws ProduitInexistantException {
            // TODO
        }

        @Override
        public void afficherCatalogueFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException {
            // TODO
        }

        @Override
        public void afficherPanierCommande(Commande commande) throws CommandeInexistanteException {
            // TODO
        }

        @Override
        public void afficherCommandesProduit(ProduitMedical produit) throws ProduitInexistantException {
            // TODO
        }

        @Override
        public void afficherCommandesDejaPassees()  {
            // TODO
        }

        @Override
        public void afficherFournisseurs()  {
            // TODO
        }

        @Override
        public void afficherStock() {
            // TODO
        }

        @Override
        public void afficherStockProduit(ProduitMedical produit) throws ProduitInexistantException {
            // TODO
        }

}
