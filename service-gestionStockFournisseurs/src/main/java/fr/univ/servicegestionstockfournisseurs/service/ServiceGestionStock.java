package fr.univ.servicegestionstockfournisseurs.service;

import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import fr.univ.servicegestionstockfournisseurs.modele.ProduitMedical;
import fr.univ.servicegestionstockfournisseurs.repository.CommandeRepository;
import fr.univ.servicegestionstockfournisseurs.repository.FournisseurRepository;
import fr.univ.servicegestionstockfournisseurs.repository.ProduitMedicalRepository;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.util.*;

@Component("serviceGestionStock")
public class ServiceGestionStock implements FacadeServiceGestionStock {

        @Autowired
        CommandeRepository commandeRepository;
        @Autowired
        FournisseurRepository fournisseurRepository;
        @Autowired
        ProduitMedicalRepository produitMedicalRepository;
//
////        //Ma Fake base de données
////        private Map<Integer, Fournisseur> fournisseurs;
////        private Map<Integer, Commande> commandes;
////        private Map<ProduitMedical,Integer> stock;
////        private Map<Integer,Integer> panier;
//
//
//        @Override
//        public void passerCommande()
//        {
//                Commande commande = new Commande(panier, new Date());
//                commandes.put(commande.getIdCommande(), commande);
//                for (Map.Entry<Integer, Integer> entry : panier.entrySet())
//                {
//                        for (Fournisseur fournisseur: fournisseurs.values()) {
//                                if (!fournisseur.getCatalogueFournisseur().containsKey(entry.getKey()))
//                                {
//                                        stock.put(fournisseur.getCatalogueFournisseur().get(entry.getKey()), entry.getValue());
//                                }
//                                else
//                                {
//                                        stock.put(fournisseur.getCatalogueFournisseur().get(entry.getKey()), stock.get(fournisseur.getCatalogueFournisseur().get(entry.getKey()))+entry.getValue());
//                                }
//                        }
//                }
//
//                panier.clear();
//        }
//
//
        @Override
        public void ajouterFournisseur( String nomFournisseur, Map<Integer,ProduitMedical> catalogueFournisseur) throws FournisseurDejaExistantException
        {
                if(fournisseurRepository.existsByNomFournisseur(nomFournisseur))
                {
                        throw new FournisseurDejaExistantException();
                }
                else
                {
                        Fournisseur fournisseur = new Fournisseur(nomFournisseur, catalogueFournisseur);
                        fournisseurRepository.save(fournisseur);
                }
        }
//
//        @Override
//        public void ajouterProduitPanier(int idProduit)
//        {
//                if(panier.containsKey(idProduit))
//                {
//                        panier.put(idProduit, panier.get(idProduit)+1);
//                }
//                else
//                {
//                        panier.put(idProduit, 1);
//                }
//        }
//
//        @Override
//        public void supprimerProduitPanier(int idProduit) throws ProduitInexistantException
//        {
//                if(panier.containsKey(idProduit))
//                {
//                        panier.remove(idProduit);
//                }
//                else
//                {
//                        throw new ProduitInexistantException();
//                }
//
//        }
//
//
        @Override
        public void annulerCommande(int idCommande) throws CommandeInexistanteException
        {
                if(commandeRepository.existsByIdCommande(idCommande))
                {
                        commandeRepository.deleteByIdCommande(idCommande);
                }
                else
                {
                        throw new CommandeInexistanteException();
                }

        }

        @Override
        public void supprimerFournisseur(int idFournisseur) throws FournisseurInexistantException
        {
                if(fournisseurRepository.existsByIdFournisseur(idFournisseur))
                {
                        fournisseurRepository.deleteByIdFournisseur(idFournisseur);
                }
                else
                {
                        throw new FournisseurInexistantException();
                }
        }

        @Override
        public void supprimerProduitFromCatalogue(int idProduit,int idFournisseur) throws ProduitInexistantException
        {
                if(fournisseurRepository.existsByIdFournisseur(idFournisseur))
                {
                        fournisseurRepository.findByIdFournisseur(idFournisseur).deleteProduit(idProduit);
                        fournisseurRepository.save(fournisseurRepository.findByIdFournisseur(idFournisseur));
                }
                else
                {
                        throw new ProduitInexistantException();
                }
        }


        @Override
        public void modifierFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException
        {
                if(fournisseurRepository.existsByIdFournisseur(fournisseur.getIdFournisseur()))
                {
                        fournisseurRepository.save(fournisseur);
                }
                else
                {
                        throw new FournisseurInexistantException();
                }
        }

        @Override
        public void modifierProduitFromCatalogue(ProduitMedical produitMedicalPatcher,int idProduit,int idFournisseur) throws ProduitInexistantException
        {
                if(fournisseurRepository.existsByIdFournisseur(idFournisseur))
                {
                        fournisseurRepository.findByIdFournisseur(idFournisseur).updateProduit(idProduit, produitMedicalPatcher);
                        fournisseurRepository.save(fournisseurRepository.findByIdFournisseur(idFournisseur));
                }
                else
                {
                        throw new ProduitInexistantException();
                }
        }

        @Override
        public Commande getCommande(int idCommande) throws CommandeInexistanteException
        {
                if(commandeRepository.existsByIdCommande(idCommande))
                {
                        return commandeRepository.findByIdCommande(idCommande);
                }
                else
                {
                        throw new CommandeInexistanteException();
                }
        }
//
        @Override
        public Fournisseur getFournisseur(int idFournisseur) throws FournisseurInexistantException
        {
                if(!fournisseurRepository.existsByIdFournisseur(idFournisseur))
                {
                        throw new FournisseurInexistantException();
                }

                return fournisseurRepository.findByIdFournisseur(idFournisseur);
        }

        @Override
        public ProduitMedical getProduitFromCatalogueFournisseur(int idProduit,int idFournisseur) throws ProduitInexistantException, FournisseurInexistantException {
                if(fournisseurRepository.existsByIdFournisseur(idFournisseur))
                {
                        if (!fournisseurRepository.findByIdFournisseur(idFournisseur).getCatalogueFournisseur().containsKey(idProduit))
                        {
                                throw new ProduitInexistantException();
                        }
                        return fournisseurRepository.findByIdFournisseur(idFournisseur).getCatalogueFournisseur().get(idProduit);
                }
                else
                {
                        throw new FournisseurInexistantException();
                }
        }
//
//        @Override
//        public ProduitMedical getProduitFromStock(int idProduit) throws ProduitInexistantException
//        {
//                for (ProduitMedical produitMedical : stock.keySet())
//                {
//                        if(produitMedical.getIdProduitMedical() == idProduit)
//                        {
//                                return produitMedical;
//                        }
//                        else
//                        {
//                                throw new ProduitInexistantException();
//                        }
//                }
//                return null;
//        }
//
        @Override
        public Map<Integer,ProduitMedical> getCatalogueFournisseur(int idFournisseur) throws FournisseurInexistantException
        {

                if(fournisseurRepository.existsByIdFournisseur(idFournisseur))
                {
                        return fournisseurRepository.findByIdFournisseur(idFournisseur).getCatalogueFournisseur();
                }
                else
                {
                        throw new FournisseurInexistantException();
                }
        }

        @Override
        public Map<Integer, Integer> getPanierCommande(int idCommande) throws CommandeInexistanteException
        {
                if(commandeRepository.existsByIdCommande(idCommande))
                {
                        return commandeRepository.findByIdCommande(idCommande).getPanierCommande();
                }
                else
                {
                        throw new CommandeInexistanteException();
                }

        }

        @Override
        public Collection<Commande> getCommandesDejaPassees()
        {
                return commandeRepository.findAll();
        }

        @Override
        public Collection<Fournisseur> getFournisseurs()
        {
                return fournisseurRepository.findAll();
        }

        @Override
        public Collection<ProduitMedical> getStock()
        {
                return produitMedicalRepository.findAll();

        }
//
//        @Override
//        public Integer getStockProduit(int idProduit) throws ProduitInexistantException
//        {
//                if(stock.containsKey(idProduit))
//                {
//                        return stock.get(idProduit);
//                }
//                else
//                {
//                        throw new ProduitInexistantException();
//                }
//        }
//
//        @Override
//        public Map<ProduitMedical,Integer> getAllProduitsFromPanier(Map<Integer,Integer> panier)
//        {
//                Map<ProduitMedical,Integer> nomProduits = new HashMap<>();
//                for (Map.Entry<Integer, Integer> entry : panier.entrySet())
//                {
//                        for (Fournisseur fournisseur: fournisseurs.values()) {
//                                if (fournisseur.getCatalogueFournisseur().conitainsKey(entry.getKey()))
//                                {
//                                        nomProduits.put(fournisseur.getCatalogueFournisseur().get(entry.getKey()),entry.getValue());
//                                }
//                        }
//                }
//                return nomProduits;
//        }




}
