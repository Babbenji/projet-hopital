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

        private Map<Integer, Fournisseur> fournisseurs;
        private Map<Integer, Commande> commandes;
        private Map<Integer, Map<ProduitMedical,Integer>> produitsEnStock;
        private Map<ProduitMedical,Integer> panier;

        @Override
        public void passerCommande()
        {
                Commande commande = new Commande(panier, new Date());
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
        public void ajouterProduitPanier(ProduitMedical produit)
        {
                if(panier.containsKey(produit))
                {
                        panier.put(produit, panier.get(produit)+1);
                }
                else
                {
                        panier.put(produit, 1);
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
        public void supprimerProduit(int idProduit) throws ProduitInexistantException
        {
                if(produitsEnStock.containsKey(idProduit))
                {
                        produitsEnStock.remove(idProduit);
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

                        fournisseurs.put(fournisseurs.get(fournisseur.getIdFournisseur()), fournisseur);
                }
                else
                {
                        throw new FournisseurInexistantException();
                }
        }

        @Override
        public void modifierProduit(int idProduit) throws ProduitInexistantException
        {
                if(produitsEnStock.containsKey(produit.getIdProduitMedical()))
                {
                        produitsEnStock.put(produit.getIdProduitMedical(), produitsEnStock.get(produit.getIdProduitMedical()));
                }
                else
                {
                        throw new ProduitInexistantException();
                }
        }

        @Override
        public void afficherCommande(int idCommande) throws CommandeInexistanteException
        {
                if(commandes.containsKey(idCommande))
                {
                        System.out.println(commandes.get(idCommande).toString());
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
        public void getProduit(int idProduit) throws ProduitInexistantException
        {
                if(produitsEnStock.containsKey(idProduit))
                {
                        System.out.println(produitsEnStock.get(idProduit).toString());
                }
                else
                {
                        throw new ProduitInexistantException();
                }

        }

        @Override
        public void getCatalogueFournisseur(int idFournisseur) throws FournisseurInexistantException
        {

                if(fournisseurs.containsKey(idFournisseur))
                {
                        System.out.println(fournisseurs.get(idFournisseur).getCatalogueFournisseur());
                }
                else
                {
                        throw new FournisseurInexistantException();
                }
        }

        @Override
        public void getPanierCommande(int idCommande) throws CommandeInexistanteException
        {
                if(commandes.containsKey(idCommande))
                {
                        System.out.println(commandes.get(idCommande).getPanierCommande());
                }
                else
                {
                        throw new CommandeInexistanteException();
                }

        }

        @Override
        public void getCommandesDejaPassees()
        {
                commandes.values().forEach(System.out::println);
        }

        @Override
        public void getFournisseurs()
        {
                fournisseurs.values().forEach(System.out::println);
        }

        @Override
        public void getStock()
        {
                produitsEnStock.values().forEach(System.out::println);

        }

        @Override
        public void getStockProduit(int idProduit) throws ProduitInexistantException
        {
                if(produitsEnStock.containsKey(idProduit))
                {
                        System.out.println(produitsEnStock.get(idProduit).get());
                }
                else
                {
                        throw new ProduitInexistantException();
                }
        }

}
