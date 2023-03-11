package fr.univ.servicegestionstockfournisseurs.service;

import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import fr.univ.servicegestionstockfournisseurs.modele.Panier;
import fr.univ.servicegestionstockfournisseurs.modele.ProduitMedical;
import fr.univ.servicegestionstockfournisseurs.repository.CommandeRepository;
import fr.univ.servicegestionstockfournisseurs.repository.FournisseurRepository;
import fr.univ.servicegestionstockfournisseurs.repository.PanierRepository;
import fr.univ.servicegestionstockfournisseurs.repository.ProduitMedicalRepository;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("serviceGestionStock")
public class ServiceGestionStock implements FacadeServiceGestionStock {

        @Autowired
        CommandeRepository commandeRepository;
        @Autowired
        FournisseurRepository fournisseurRepository;
        @Autowired
        ProduitMedicalRepository produitMedicalRepository;
        @Autowired
        PanierRepository panierRepository;

        //
////        //Ma Fake base de données
////        private Map<Integer, Fournisseur> fournisseurs;
////        private Map<Integer, Commande> commandes;
////        private Map<ProduitMedical,Integer> stock;
////        private Map<Integer,Integer> panier;


        @Override
        public void passerCommande(int idPanier) throws ProduitNonDisponibleException
        {
                Commande commande = new Commande(new Date());
                commandeRepository.save(commande);
                Panier p = panierRepository.findByIdPanier(idPanier);
                Map<ProduitMedical, Integer> panier = p.getPanierProduits();
                if (panierRepository.existsByIdPanier(idPanier)) {
                    //    Map<ProduitMedical, Integer> nomProduits = new HashMap<>();
                        for (Map.Entry<ProduitMedical, Integer> entry : panier.entrySet()) {
                                for (Fournisseur fournisseur : fournisseurRepository.findAll()) {
                                        if (fournisseur.getCatalogueFournisseur().containsKey(entry.getKey())) {
                                             //   nomProduits.put(fournisseur.getCatalogueFournisseur().get(entry.getKey()), entry.getValue());
                                                //Panier : ([Seringue : 4], [Paracetamole : 7])
                                                for (ProduitMedical pro : produitMedicalRepository.findAll()){
                                                        if (pro.equals(entry.getKey())){
                                                                // pro c'est un produit dans le bdd et si pro est égale au produit dans le panier, alors son stock augmente du int indiqué dans le panier

                                                                pro.getStockProduitMedical() += entry.getValue();
                                                        }
                                                }

                                        }
                                        throw new ProduitNonDisponibleException();
                                }
                        }

                }
                {
                        //verif si un fournisseur a les produit
                        // add le produit dans le stock
                        // calculer le prix total de la commande

                        for (Fournisseur fournisseur: fournisseurRepository.findAll()) {
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
//
//
        @Override
        public void ajouterFournisseur(String nomFournisseur, Map<Integer, ProduitMedical> catalogueFournisseur) throws FournisseurDejaExistantException {
                if (fournisseurRepository.existsByNomFournisseur(nomFournisseur)) {
                        throw new FournisseurDejaExistantException();
                } else {
                        Fournisseur fournisseur = new Fournisseur(nomFournisseur, catalogueFournisseur);
                        fournisseurRepository.save(fournisseur);
                }
        }

        @Override
        public void ajouterProduitPanier(int idPanier, ProduitMedical produitMedical) {
                if (panierRepository.existsByIdPanier(idPanier)) {

                        Panier p = panierRepository.findByIdPanier(idPanier);
                        Map<ProduitMedical, Integer> panier = p.getPanierProduits();

                        if (panier.containsKey(produitMedical)) {
                                panier.put(produitMedical, panier.get(produitMedical) + 1);
                                panierRepository.save(p);
                        } else {
                                panier.put(produitMedical, 1);
                                panierRepository.save(p);

                        }
                } else {
                        Map<ProduitMedical, Integer> newpanier = new HashMap<>();
                        newpanier.put(produitMedical, 1);
                        Panier pa = new Panier(newpanier);
                        panierRepository.save(pa);
                }
        }


        @Override
        public void supprimerProduitPanier(int idPanier, int idProduit) throws ProduitInexistantException {
                if (panierRepository.existsByIdPanier(idPanier)) {
                        Panier p = panierRepository.findByIdPanier(idPanier);
                        Map<ProduitMedical, Integer> panier = p.getPanierProduits();

                        if (panier.containsKey(idProduit)) {
                                panier.remove(idProduit);
                                panierRepository.save(p);
                        } else {
                                throw new ProduitInexistantException();
                        }
                }

        }


        @Override
        public void annulerCommande(int idCommande) throws CommandeInexistanteException {
                if (commandeRepository.existsByIdCommande(idCommande)) {
                        commandeRepository.deleteByIdCommande(idCommande);
                } else {
                        throw new CommandeInexistanteException();
                }

        }

        @Override
        public void supprimerFournisseur(int idFournisseur) throws FournisseurInexistantException {
                if (fournisseurRepository.existsByIdFournisseur(idFournisseur)) {
                        fournisseurRepository.deleteByIdFournisseur(idFournisseur);
                } else {
                        throw new FournisseurInexistantException();
                }
        }

        @Override
        public void supprimerProduitFromCatalogue(int idProduit, int idFournisseur) throws ProduitInexistantException {
                if (fournisseurRepository.existsByIdFournisseur(idFournisseur)) {
                        fournisseurRepository.findByIdFournisseur(idFournisseur).deleteProduit(idProduit);
                        fournisseurRepository.save(fournisseurRepository.findByIdFournisseur(idFournisseur));
                } else {
                        throw new ProduitInexistantException();
                }
        }


        @Override
        public void modifierFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException {
                if (fournisseurRepository.existsByIdFournisseur(fournisseur.getIdFournisseur())) {
                        fournisseurRepository.save(fournisseur);
                } else {
                        throw new FournisseurInexistantException();
                }
        }

        @Override
        public void modifierProduitFromCatalogue(ProduitMedical produitMedicalPatcher, int idProduit, int idFournisseur) throws ProduitInexistantException {
                if (fournisseurRepository.existsByIdFournisseur(idFournisseur)) {
                        fournisseurRepository.findByIdFournisseur(idFournisseur).updateProduit(idProduit, produitMedicalPatcher);
                        fournisseurRepository.save(fournisseurRepository.findByIdFournisseur(idFournisseur));
                } else {
                        throw new ProduitInexistantException();
                }
        }

        @Override
        public Commande getCommande(int idCommande) throws CommandeInexistanteException {
                if (commandeRepository.existsByIdCommande(idCommande)) {
                        return commandeRepository.findByIdCommande(idCommande);
                } else {
                        throw new CommandeInexistanteException();
                }
        }

        //
        @Override
        public Fournisseur getFournisseur(int idFournisseur) throws FournisseurInexistantException {
                if (!fournisseurRepository.existsByIdFournisseur(idFournisseur)) {
                        throw new FournisseurInexistantException();
                }

                return fournisseurRepository.findByIdFournisseur(idFournisseur);
        }

        @Override
        public ProduitMedical getProduitFromCatalogueFournisseur(int idProduit, int idFournisseur) throws ProduitInexistantException, FournisseurInexistantException {
                if (fournisseurRepository.existsByIdFournisseur(idFournisseur)) {
                        if (!fournisseurRepository.findByIdFournisseur(idFournisseur).getCatalogueFournisseur().containsKey(idProduit)) {
                                throw new ProduitInexistantException();
                        }
                        return fournisseurRepository.findByIdFournisseur(idFournisseur).getCatalogueFournisseur().get(idProduit);
                } else {
                        throw new FournisseurInexistantException();
                }
        }


        @Override
        public Map<Integer, ProduitMedical> getCatalogueFournisseur(int idFournisseur) throws FournisseurInexistantException {

                if (fournisseurRepository.existsByIdFournisseur(idFournisseur)) {
                        return fournisseurRepository.findByIdFournisseur(idFournisseur).getCatalogueFournisseur();
                } else {
                        throw new FournisseurInexistantException();
                }
        }



        @Override
        public Collection<Commande> getCommandesDejaPassees() {
                return commandeRepository.findAll();
        }

        @Override
        public Collection<Fournisseur> getFournisseurs() {
                return fournisseurRepository.findAll();
        }

        @Override
        public Collection<ProduitMedical> getStock() {
                return produitMedicalRepository.findAll();

        }

        @Override
        public ProduitMedical getProduitMedicaleByNom(String nomProduit) {
                return produitMedicalRepository.findProduitMedicalByNomProduitMedical(nomProduit);
        }


        @Override
        public int getStockProduit(int idProduit) throws ProduitInexistantException {

                if (produitMedicalRepository.existsByIdProduitMedical(idProduit)) {
                        ProduitMedical produitMedical = produitMedicalRepository.findByIdProduitMedical(idProduit);
                        return produitMedical.getStockProduitMedical();
                } else {
                        throw new ProduitInexistantException();
                }


        }

        @Override
        public Map<ProduitMedical, Integer> getAllProduitsFromPanier(int idPanier) throws PanierInexistantException {
                if (panierRepository.existsByIdPanier(idPanier)) {
                        return panierRepository.findByIdPanier(idPanier).getPanierProduits();

                }
                throw new PanierInexistantException();

        }





}
