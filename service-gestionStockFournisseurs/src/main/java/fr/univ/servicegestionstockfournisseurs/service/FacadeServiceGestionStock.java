package fr.univ.servicegestionstockfournisseurs.service;

import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import fr.univ.servicegestionstockfournisseurs.modele.ProduitMedical;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.*;

public interface FacadeServiceGestionStock {
    void passerCommande();

    void ajouterFournisseur(Fournisseur fournisseur) throws FournisseurDejaExistantException;

    void ajouterProduitPanier(ProduitMedical produit) throws ProduitDejaExistantException;

    void annulerCommande(int idCommande) throws CommandeInexistanteException;

    void supprimerFournisseur(int idFournisseur) throws FournisseurInexistantException;

    void supprimerProduit(int idProduit) throws ProduitInexistantException;

    void modifierFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException;

    void modifierProduit(int idProduit) throws ProduitInexistantException;

    void afficherCommande(int idCommande) throws CommandeInexistanteException;

    Fournisseur getFournisseur(int idFournisseur) throws FournisseurInexistantException;

    void getProduit(int idProduit) throws ProduitInexistantException;

    void getCatalogueFournisseur(int idFournisseur) throws FournisseurInexistantException;

    void getPanierCommande(int idCommande) throws CommandeInexistanteException;

    void getCommandesDejaPassees();

    void getFournisseurs();

    void getStock();

    void getStockProduit(int idProduit) throws ProduitInexistantException;
}
