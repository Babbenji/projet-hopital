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
        public void annulerCommande(Commande commande) throws CommandeInexistanteException
        {
                if(commandes.containsKey(commande.getIdCommande()))
                {
                        commandes.remove(commande.getIdCommande());
                }
                else
                {
                        throw new CommandeInexistanteException();
                }

        }

        @Override
        public void supprimerFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException
        {
                if(fournisseurs.containsKey(fournisseur.getIdFournisseur()))
                {
                        fournisseurs.remove(fournisseur.getIdFournisseur());
                }
                else
                {
                        throw new FournisseurInexistantException();
                }
        }

        @Override
        public void supprimerProduit(ProduitMedical produit) throws ProduitInexistantException
        {
                if(produitsEnStock.containsKey(produit.getIdProduitMedical()))
                {
                        produitsEnStock.remove(produit.getPrixProduitMedical());
                }
                else
                {
                        throw new ProduitInexistantException();
                }
        }

        @Override
        public void modifierCommande(Commande commande) throws CommandeInexistanteException
        {
                if(commandes.containsKey(commande.getIdCommande()))
                {
                        commandes.put(commande.getIdCommande(), commande);
                }
                else
                {
                        throw new CommandeInexistanteException();
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
        public void modifierProduit(ProduitMedical produit) throws ProduitInexistantException
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
        public void afficherCommande(Commande commande) throws CommandeInexistanteException
        {
                if(commandes.containsKey(commande.getIdCommande()))
                {
                        System.out.println(commande);
                }
                else
                {
                        throw new CommandeInexistanteException();
                }
        }

        @Override
        public void afficherFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException
        {
                if(fournisseurs.containsKey(fournisseur.getIdFournisseur()))
                {
                        System.out.println(fournisseur);
                }
                else {
                        throw new FournisseurInexistantException();
                }
        }

        @Override
        public void afficherProduit(ProduitMedical produit) throws ProduitInexistantException
        {
                if(produitsEnStock.containsKey(produit.getIdProduitMedical()))
                {
                        System.out.println(produit);
                }
                else
                {
                        throw new ProduitInexistantException();
                }

        }

        @Override
        public void afficherCatalogueFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException
        {
                if(fournisseurs.containsKey(fournisseur.getIdFournisseur()))
                {
                        System.out.println(fournisseur.getCatalogueFournisseur());
                }
                else
                {
                        throw new FournisseurInexistantException();
                }
        }

        @Override
        public void afficherPanierCommande(Commande commande) throws CommandeInexistanteException
        {
                if(commandes.containsKey(commande.getIdCommande()))
                {
                        System.out.println(commande.getPanierCommande());
                }
                else
                {
                        throw new CommandeInexistanteException();
                }

        }

        @Override
        public void afficherCommandesProduit(ProduitMedical produit) throws ProduitInexistantException
        {

        }

        @Override
        public void afficherCommandesDejaPassees()
        {
                commandes.values().forEach(System.out::println);
        }

        @Override
        public void afficherFournisseurs()
        {
                fournisseurs.values().forEach(System.out::println);
        }

        @Override
        public void afficherStock()
        {
                produitsEnStock.values().forEach(System.out::println);

        }

        @Override
        public void afficherStockProduit(ProduitMedical produit) throws ProduitInexistantException
        {
                if(produitsEnStock.containsKey(produit.getIdProduitMedical()))
                {
                        System.out.println(produitsEnStock.get(produit.getIdProduitMedical()).get(produit));
                }
                else
                {
                        throw new ProduitInexistantException();
                }
        }

}
