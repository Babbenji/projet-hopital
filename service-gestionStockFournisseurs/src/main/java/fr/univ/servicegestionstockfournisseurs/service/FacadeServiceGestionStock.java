package fr.univ.servicegestionstockfournisseurs.service;

import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import fr.univ.servicegestionstockfournisseurs.modele.ProduitMedical;
import fr.univ.servicegestionstockfournisseurs.modele.Utilisateur;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.*;

import java.util.Collection;
import java.util.Map;

public interface FacadeServiceGestionStock {
    void passerCommande(int idPanier) throws PanierInexistantException, ProduitNonDisponibleException;

    void ajouterUtilisateur(Utilisateur utilisateur) throws UtilisateurDejaExistantException;

    void ajouterProduit(String nomProduit, double prixProduit, String descriptionProduit) throws ProduitDejaExistantException;

    void ajouterProduitFournisseur(int idFournisseur, String produitMedical) throws FournisseurInexistantException,ProduitDejaDansCatalogueException;

    void ajouterFournisseur( Fournisseur fournisseur) throws FournisseurDejaExistantException;

    void ajouterProduitPanier(int idPanier, int idProduit,int quantite) throws ProduitDejaExistantException, UtilisateurInexistantException;

    void annulerCommande(int idCommande) throws CommandeInexistanteException;

    void supprimerFournisseur(int idFournisseur) throws FournisseurInexistantException;

    void supprimerProduitFromCatalogue(int idProduit,int idFournisseur) throws ProduitInexistantException;

    void supprimerProduitPanier(int idUtilisateur, int idProduit) throws ProduitInexistantException;

    void modifierFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException;

    //void modifierProduitFromCatalogue(ProduitMedical produitMedical,int idProduit, int idFournisseur) throws ProduitInexistantException;

    Commande getCommande(int idCommande) throws CommandeInexistanteException;

    Fournisseur getFournisseur(int idFournisseur) throws FournisseurInexistantException;

    //ProduitMedical getProduitFromCatalogueFournisseur(int idProduit,int idFournisseur) throws ProduitInexistantException, FournisseurInexistantException;

    int getStockProduit(int idProduit) throws ProduitInexistantException;

    Map<Integer,String> getCatalogueFournisseur(int idFournisseur) throws FournisseurInexistantException;

    Collection<Commande> getCommandesDejaPassees();

    Collection<Fournisseur> getFournisseurs();

    Collection<ProduitMedical> getStock();

    ProduitMedical getProduitMedicaleByNom(String nomProduit);

    Map<Integer, Integer> getAllProduitsFromPanier(int idUtilisateur) throws UtilisateurInexistantException;
}
