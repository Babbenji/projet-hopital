package fr.univ.servicegestionstockfournisseurs.service;

import fr.univ.servicegestionstockfournisseurs.modele.*;
import fr.univ.servicegestionstockfournisseurs.repository.*;
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
        UtilisateurRepository utilisateurRepository;

        @Override
        public void passerCommande(int idUtilisateur) throws ProduitNonDisponibleException {
                Commande commande = new Commande(new Date());

                if (utilisateurRepository.existsUtilisateurByIdUtilisateur(idUtilisateur))
                {
                        Map<Integer, Integer> panier = utilisateurRepository.findUtilisateurByIdUtilisateur(idUtilisateur).getPanierUtilisateur();
                        for (Map.Entry<Integer, Integer> entry : panier.entrySet())
                        {
                                for (Fournisseur fournisseur : fournisseurRepository.findAll())
                                {
                                        if (fournisseur.getCatalogueFournisseur().containsKey(entry.getKey()))
                                        {
                                                for (ProduitMedical pro : produitMedicalRepository.findAll())
                                                {
                                                        if (pro.getIdProduitMedical()==entry.getKey())
                                                        {
                                                                // pro c'est un produit dans le bdd et si pro est égale au produit dans le panier, alors son stock augmente du int indiqué dans le panier
                                                                pro.setStockProduitMedical(pro.getStockProduitMedical()+entry.getValue());
                                                                produitMedicalRepository.save(pro);
                                                        }
                                                }
                                        }
                                        throw new ProduitNonDisponibleException();
                                }
                                ProduitMedical produit = produitMedicalRepository.findByIdProduitMedical(entry.getKey());
                                commande.setPrixCommande(commande.getPrixCommande() + produit.getPrixProduitMedical() * entry.getValue());
                        }
                }
                commandeRepository.save(commande);
        }

        @Override
        public void ajouterUtilisateur(Utilisateur utilisateur) throws UtilisateurDejaExistantException {
                if (utilisateurRepository.existsUtilisateurByIdUtilisateur(utilisateur.getIdUtilisateur())) {
                        throw new UtilisateurDejaExistantException();
                } else {
                        utilisateurRepository.save(utilisateur);
                }
        }

        @Override
        public void ajouterProduit(String nomProduit, double prixProduit, String descriptionProduit) throws ProduitDejaExistantException {
                if (produitMedicalRepository.existsByNomProduitMedical(nomProduit)) {
                        throw new ProduitDejaExistantException();
                } else {
                        ProduitMedical produitMedical = new ProduitMedical(nomProduit,prixProduit, descriptionProduit );
                        produitMedicalRepository.save(produitMedical);
                }
        }

        @Override
        public void ajouterProduitFournisseur(int idFournisseur, String produitMedical) throws FournisseurInexistantException, ProduitDejaDansCatalogueException
        {
                if (fournisseurRepository.existsByIdFournisseur(idFournisseur)) {
                        Fournisseur fournisseur = fournisseurRepository.findByIdFournisseur(idFournisseur);
                        ProduitMedical produitMedical1 = produitMedicalRepository.findProduitMedicalByNomProduitMedical(produitMedical);
                        if(fournisseurRepository.existsByCatalogueFournisseur(produitMedical1.getIdProduitMedical()))
                        {
                                throw new ProduitDejaDansCatalogueException();
                        }
                        fournisseur.getCatalogueFournisseur().put(produitMedical1.getIdProduitMedical(), produitMedical1.getNomProduitMedical());
                        fournisseurRepository.save(fournisseur);
                } else {
                        throw new FournisseurInexistantException();
                }
        }

        @Override
        public void ajouterFournisseur(Fournisseur fournisseur) throws FournisseurDejaExistantException {
                if (fournisseurRepository.existsByNomFournisseur(fournisseur.getNomFournisseur())) {
                        throw new FournisseurDejaExistantException();
                } else {
                        fournisseurRepository.save(fournisseur);
                }
        }

        @Override
        public void ajouterProduitPanier(int idUtilisateur, int idProduit,int quantite) throws UtilisateurInexistantException {
                if (utilisateurRepository.existsUtilisateurByIdUtilisateur(idUtilisateur))
                {
                        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByIdUtilisateur(idUtilisateur);
                        Map<Integer, Integer> panier = utilisateur.getPanierUtilisateur();
                        if (panier.containsKey(idProduit)) {
                                panier.put(idProduit, panier.get(idProduit) + quantite);
                                utilisateurRepository.save(utilisateur);
                        } else {
                                panier.put(idProduit, 1);
                                utilisateurRepository.save(utilisateur);
                        }
                }
                else {
                        throw new UtilisateurInexistantException();
                }
        }


        @Override
        public void supprimerProduitPanier(int idUtilisateur, int idProduit) throws ProduitInexistantException {
                if (utilisateurRepository.existsUtilisateurByIdUtilisateur(idUtilisateur)) {
                        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByIdUtilisateur(idUtilisateur);
                        Map<Integer, Integer> panier = utilisateur.getPanierUtilisateur();
                        if (panier.containsKey(idProduit)) {
                                panier.remove(idProduit);
                                utilisateurRepository.save(utilisateur);
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
                } else {
                        throw new ProduitInexistantException();
                }
        }


        @Override
        public void modifierFournisseur(Fournisseur fournisseur) throws FournisseurInexistantException
        {
                if (fournisseurRepository.existsByIdFournisseur(fournisseur.getIdFournisseur())) {
                        fournisseurRepository.save(fournisseur);
                } else {
                        throw new FournisseurInexistantException();
                }
        }

//        @Override
//        public void modifierProduitFromCatalogue(ProduitMedical produitMedicalPatcher, int idProduit, int idFournisseur) throws ProduitInexistantException {
//                if (fournisseurRepository.existsByIdFournisseur(idFournisseur)) {
//                        fournisseurRepository.findByIdFournisseur(idFournisseur).updateProduit(idProduit, produitMedicalPatcher);
//                        fournisseurRepository.save(fournisseurRepository.findByIdFournisseur(idFournisseur));
//                } else {
//                        throw new ProduitInexistantException();
//                }
//        }

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

//        @Override
//        public ProduitMedical getProduitFromCatalogueFournisseur(int idProduit, int idFournisseur) throws ProduitInexistantException, FournisseurInexistantException {
//                if (fournisseurRepository.existsByIdFournisseur(idFournisseur)) {
//                        if (!fournisseurRepository.findByIdFournisseur(idFournisseur).getCatalogueFournisseur().containsKey(idProduit)) {
//                                throw new ProduitInexistantException();
//                        }
//                        return fournisseurRepository.findByIdFournisseur(idFournisseur).getCatalogueFournisseur().get(idProduit);
//                } else {
//                        throw new FournisseurInexistantException();
//                }
//        }


        @Override
        public Map<Integer, String> getCatalogueFournisseur(int idFournisseur) throws FournisseurInexistantException {

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
        public Map<Integer, Integer> getAllProduitsFromPanier(int idUtilisateur) throws UtilisateurInexistantException
        {
                if (utilisateurRepository.existsUtilisateurByIdUtilisateur(idUtilisateur))
                        return utilisateurRepository.findUtilisateurByIdUtilisateur(idUtilisateur).getPanierUtilisateur();

                throw new UtilisateurInexistantException();

        }





}
