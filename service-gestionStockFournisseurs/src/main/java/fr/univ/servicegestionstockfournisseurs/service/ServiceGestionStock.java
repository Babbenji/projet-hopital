package fr.univ.servicegestionstockfournisseurs.service;

import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import fr.univ.servicegestionstockfournisseurs.modele.ProduitMedical;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.*;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.util.*;

@Component("serviceGestionStock")
public class ServiceGestionStock implements FacadeServiceGestionStock {

        //Ma Fake base de données
        private Map<Integer, Fournisseur> fournisseurs;
        private Map<Integer, Commande> commandes;
        private Map<ProduitMedical,Integer> stock;
        private Map<Integer,Integer> panier;

        @Override
        public void passerCommande()
        {
                Commande commande = new Commande(panier, new Date());
                commandes.put(commande.getIdCommande(), commande);
                for (Map.Entry<Integer, Integer> entry : panier.entrySet())
                {
                        for (Fournisseur fournisseur: fournisseurs.values()) {
                                if (!fournisseur.getCatalogueFournisseur().containsKey(entry.getKey()))
                                {
                                        stock.put(fournisseur.getCatalogueFournisseur().get(entry.getKey()), entry.getValue());
                                }
                                else
                                {
                                        stock.put(fournisseur.getCatalogueFournisseur().get(entry.getKey()), stock.get(fournisseur.getCatalogueFournisseur().get(entry.getKey()))+entry.getValue());
                                }
                        }
                }

                panier.clear();
        }


        @Override
        public void ajouterFournisseur(Fournisseur fournisseur) throws FournisseurDejaExistantException
        {
                if(fournisseurs.containsKey(fournisseur.getIdFournisseur()))
                {
                        throw new FournisseurDejaExistantException();
                }
                else
                {
                        fournisseurs.put(fournisseur.getIdFournisseur(), fournisseur);
                }
        }

        @Override
        public void ajouterProduitPanier(int idProduit)
        {
                if(panier.containsKey(idProduit))
                {
                        panier.put(idProduit, panier.get(idProduit)+1);
                }
                else
                {
                        panier.put(idProduit, 1);
                }
        }

        @Override
        public void supprimerProduitPanier(int idProduit) throws ProduitInexistantException
        {
                if(panier.containsKey(idProduit))
                {
                        panier.remove(idProduit);
                }
                else
                {
                        throw new ProduitInexistantException();
                }

        }


        @Override
        public void annulerCommande(int idCommande) throws CommandeInexistanteException
        {
                if(commandes.containsKey(idCommande))
                {
                        commandes.remove(idCommande);
                }
                else
                {
                        throw new CommandeInexistanteException();
                }

        }

        @Override
        public void supprimerFournisseur(int idFournisseur) throws FournisseurInexistantException
        {
                if(fournisseurs.containsKey(idFournisseur))
                {
                        fournisseurs.remove(idFournisseur);
                }
                else
                {
                        throw new FournisseurInexistantException();
                }
        }

        @Override
        public void supprimerProduitFromCatalogue(int idProduit,int idFournisseur) throws ProduitInexistantException
        {
                if(fournisseurs.containsKey(idFournisseur))
                {
                        fournisseurs.get(idFournisseur).deleteProduit(idProduit);
                }
                else
                {
                        throw new ProduitInexistantException();
                }
        }


        @Override
        public void modifierFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException
        {
                if(fournisseurs.containsKey(fournisseur.getIdFournisseur()))
                {
                        fournisseurs.put(fournisseur.getIdFournisseur(), fournisseur);
                }
                else
                {
                        throw new FournisseurInexistantException();
                }
        }

        @Override
        public void modifierProduitFromCatalogue(ProduitMedical produitMedicalPatcher,int idProduit,int idFournisseur) throws ProduitInexistantException
        {
                if(fournisseurs.containsKey(idFournisseur))
                {
                        fournisseurs.get(idFournisseur).updateProduit(idProduit,produitMedicalPatcher);
                }
                else
                {
                        throw new ProduitInexistantException();
                }
        }

        @Override
        public Commande getCommande(int idCommande) throws CommandeInexistanteException
        {
                if(commandes.containsKey(idCommande))
                {
                        return commandes.get(idCommande);
                }
                else
                {
                        throw new CommandeInexistanteException();
                }
        }

        @Override
        public Fournisseur getFournisseur(int idFournisseur) throws FournisseurInexistantException
        {
                if(!fournisseurs.containsKey(idFournisseur))
                {
                        throw new FournisseurInexistantException();
                }

                return fournisseurs.get(idFournisseur);
        }

        @Override
        public ProduitMedical getProduitFromCatalogueFournisseur(int idProduit,int idFournisseur) throws ProduitInexistantException, FournisseurInexistantException {
                if(fournisseurs.containsKey(idFournisseur))
                {
                        if (!fournisseurs.get(idFournisseur).getCatalogueFournisseur().containsKey(idProduit))
                        {
                                throw new ProduitInexistantException();
                        }
                        return fournisseurs.get(idFournisseur).getCatalogueFournisseur().get(idProduit);
                }
                else
                {
                        throw new FournisseurInexistantException();
                }
        }

        @Override
        public ProduitMedical getProduitFromStock(int idProduit) throws ProduitInexistantException
        {
                for (ProduitMedical produitMedical : stock.keySet())
                {
                        if(produitMedical.getIdProduitMedical() == idProduit)
                        {
                                return produitMedical;
                        }
                        else
                        {
                                throw new ProduitInexistantException();
                        }
                }
                return null;
        }

        @Override
        public Map<Integer,ProduitMedical> getCatalogueFournisseur(int idFournisseur) throws FournisseurInexistantException
        {

                if(fournisseurs.containsKey(idFournisseur))
                {
                        return fournisseurs.get(idFournisseur).getCatalogueFournisseur();
                }
                else
                {
                        throw new FournisseurInexistantException();
                }
        }

        @Override
        public Map<Integer, Integer> getPanierCommande(int idCommande) throws CommandeInexistanteException
        {
                if(commandes.containsKey(idCommande))
                {
                        return commandes.get(idCommande).getPanierCommande();
                }
                else
                {
                        throw new CommandeInexistanteException();
                }

        }

        @Override
        public Collection<Commande> getCommandesDejaPassees()
        {
                return commandes.values();
        }

        @Override
        public Collection<Fournisseur> getFournisseurs()
        {
                return fournisseurs.values();
        }

        @Override
        public Collection<ProduitMedical> getStock()
        {
                return stock.keySet();

        }

        @Override
        public Integer getStockProduit(int idProduit) throws ProduitInexistantException
        {
                if(stock.containsKey(idProduit))
                {
                        return stock.get(idProduit);
                }
                else
                {
                        throw new ProduitInexistantException();
                }
        }

        @Override
        public Map<ProduitMedical,Integer> getAllProduitsFromPanier(Map<Integer,Integer> panier)
        {
                Map<ProduitMedical,Integer> nomProduits = new HashMap<>();
                for (Map.Entry<Integer, Integer> entry : panier.entrySet())
                {
                        for (Fournisseur fournisseur: fournisseurs.values()) {
                                if (fournisseur.getCatalogueFournisseur().containsKey(entry.getKey()))
                                {
                                        nomProduits.put(fournisseur.getCatalogueFournisseur().get(entry.getKey()),entry.getValue());
                                }
                        }
                }
                return nomProduits;
        }




}
