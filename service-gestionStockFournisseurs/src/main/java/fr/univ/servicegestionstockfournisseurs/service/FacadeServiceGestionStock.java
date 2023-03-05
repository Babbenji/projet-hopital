package fr.univ.servicegestionstockfournisseurs.service;

import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import fr.univ.servicegestionstockfournisseurs.modele.ProduitMedical;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface FacadeServiceGestionStock {
    void passerCommande();

    void ajouterFournisseur(Fournisseur fournisseur) throws FournisseurDejaExistantException;

    void ajouterProduitPanier(int idProduit) throws ProduitDejaExistantException;


    void annulerCommande(int idCommande) throws CommandeInexistanteException;

    void supprimerFournisseur(int idFournisseur) throws FournisseurInexistantException;

    void supprimerProduitFromCatalogue(int idProduit,int idFournisseur) throws ProduitInexistantException;

    void supprimerProduitPanier(int idProduit) throws ProduitInexistantException;

    void modifierFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException;

    void modifierProduitFromCatalogue(ProduitMedical produitMedical,int idProduit, int idFournisseur) throws ProduitInexistantException;

    Commande getCommande(int idCommande) throws CommandeInexistanteException;

    Fournisseur getFournisseur(int idFournisseur) throws FournisseurInexistantException;

    ProduitMedical getProduitFromCatalogueFournisseur(int idProduit,int idFournisseur) throws ProduitInexistantException, FournisseurInexistantException;

    ProduitMedical getProduitFromStock(int idProduit) throws ProduitInexistantException;

    Map<Integer,ProduitMedical> getCatalogueFournisseur(int idFournisseur) throws FournisseurInexistantException;

    Map<Integer,Integer> getPanierCommande(int idCommande) throws CommandeInexistanteException;

    Collection<Commande> getCommandesDejaPassees();

    Collection<Fournisseur> getFournisseurs();

    Collection<ProduitMedical> getStock();

    Integer getStockProduit(int idProduit) throws ProduitInexistantException;

    Map<ProduitMedical,Integer> getAllProduitsFromPanier(Map<Integer,Integer> panierCommande);

}
