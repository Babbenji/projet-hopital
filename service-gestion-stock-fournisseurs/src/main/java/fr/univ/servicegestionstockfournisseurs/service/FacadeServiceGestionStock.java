package fr.univ.servicegestionstockfournisseurs.service;

import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import fr.univ.servicegestionstockfournisseurs.modele.DTO.FactureDTO;
import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import fr.univ.servicegestionstockfournisseurs.modele.ProduitMedical;
import fr.univ.servicegestionstockfournisseurs.modele.Utilisateur;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.*;
import java.util.Collection;
import java.util.Map;


public interface FacadeServiceGestionStock {
    void passerCommande(int idPanier) throws UtilisateurInexistantException;

    void ajouterUtilisateur(Utilisateur utilisateur) throws UtilisateurDejaExistantException;

    void ajouterProduit(String nomProduit, double prixProduit, String descriptionProduit) throws ProduitDejaExistantException;

    void ajouterProduitFournisseur(int idFournisseur, int idProduit) throws FournisseurInexistantException, ProduitDejaDansCatalogueException, ProduitInexistantException;

    void ajouterFournisseur( Fournisseur fournisseur) throws FournisseurDejaExistantException;

    void ajouterProduitPanier(int idPanier, int idProduit,int quantite) throws UtilisateurInexistantException, ProduitInexistantException;

    void annulerCommande(int idCommande) throws CommandeInexistanteException;

    void supprimerFournisseur(int idFournisseur) throws FournisseurInexistantException;

    void supprimerProduitFromCatalogue(int idProduit,int idFournisseur) throws FournisseurInexistantException;

    void supprimerProduitPanier(int idUtilisateur, int idProduit) throws ProduitInexistantException;

    void modifierFournisseur(int idFournisseur,Map<String, Object> attributsModifies) throws FournisseurInexistantException;

    void modifierProduit(int idProduit,Map<String, Object> attributsModifies) throws ProduitInexistantException;

    void modifierQuantiteProduitMedical(FactureDTO factureDTO) throws ProduitInexistantException, ProduitNonDisponibleException;

    Commande getCommande(int idCommande) throws CommandeInexistanteException;

    Fournisseur getFournisseur(int idFournisseur) throws FournisseurInexistantException;

    int getStockProduit(int idProduit) throws ProduitInexistantException;

    Map<Integer,String> getCatalogueFournisseur(int idFournisseur) throws FournisseurInexistantException;

    Collection<Commande> getCommandesDejaPassees();

    Collection<Fournisseur> getFournisseurs();

    Collection<ProduitMedical> getStock();

    ProduitMedical getProduitMedicaleByNom(String nomProduit);

    ProduitMedical getProduitMedicaleById(int idProduit) throws ProduitInexistantException;

    String getAllProduitsFromPanier(int idUtilisateur) throws UtilisateurInexistantException;

    Map<String, Integer> getPanierFromCommande(int idCommande) throws CommandeInexistanteException;

}
