package fr.univ.servicegestionstockfournisseurs.service;

import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import fr.univ.servicegestionstockfournisseurs.modele.ProduitMedical;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.*;

public interface FacadeServiceGestionStock {
    void passerCommande(Commande commande);

    void ajouterFournisseur(Fournisseur fournisseur) throws FournisseurDejaExistantException;

    void ajouterProduitPanier(ProduitMedical produit) throws ProduitDejaExistantException;

    void annulerCommande(Commande commande) throws CommandeInexistanteException;

    void supprimerFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException;

    void supprimerProduit(ProduitMedical produit) throws ProduitInexistantException;

    void modifierCommande(Commande commande) throws CommandeInexistanteException;

    void modifierFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException;

    void modifierProduit(ProduitMedical produit) throws ProduitInexistantException;

    void afficherCommande(Commande commande) throws CommandeInexistanteException;

    void afficherFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException;

    void afficherProduit(ProduitMedical produit) throws ProduitInexistantException;

    void afficherCatalogueFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException;

    void afficherPanierCommande(Commande commande) throws CommandeInexistanteException;

    void afficherCommandesProduit(ProduitMedical produit) throws ProduitInexistantException;

    void afficherCommandesDejaPassees();

    void afficherFournisseurs();

    void afficherStock();

    void afficherStockProduit(ProduitMedical produit) throws ProduitInexistantException;
}
