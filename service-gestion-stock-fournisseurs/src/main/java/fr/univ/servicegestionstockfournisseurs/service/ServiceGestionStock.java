package fr.univ.servicegestionstockfournisseurs.service;

import fr.univ.servicegestionstockfournisseurs.modele.*;
import fr.univ.servicegestionstockfournisseurs.modele.DTO.FactureDTO;
import fr.univ.servicegestionstockfournisseurs.producer.RabbitMQProducer;
import fr.univ.servicegestionstockfournisseurs.repository.*;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("serviceGestionStock")
public class ServiceGestionStock implements FacadeServiceGestionStock {

        @Autowired
        CommandeRepository commandeRepository;
        @Autowired
        FournisseurRepository fournisseurRepository;
        @Autowired
        ProduitMedicalRepository produitMedicalRepository;
        @Autowired
        UtilisateurRepository utilisateurRepository;

        @Autowired
        RabbitMQProducer rabbitMQProducer;

        @Override
        public void passerCommande(int idUtilisateur) throws UtilisateurInexistantException {
                Commande commande = new Commande(new Date());

                if (utilisateurRepository.existsUtilisateurByIdUtilisateur(idUtilisateur))
                {
                        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByIdUtilisateur(idUtilisateur);
                        System.out.println(utilisateur);
                        Map<Integer, Integer> panier = utilisateur.getPanierUtilisateur();
                        System.out.println(panier);
                        for (Map.Entry<Integer, Integer> entry : panier.entrySet())
                        {
                                System.out.println(entry.getKey());

                                ProduitMedical produit = produitMedicalRepository.findByIdProduitMedical(entry.getKey());
                                System.out.println(produit);
                                for (Fournisseur fournisseur : fournisseurRepository.findAll())
                                {
                                        if (fournisseur.getCatalogueFournisseur().containsKey(entry.getKey()))
                                        {
                                                produit.setStockProduitMedical(produit.getStockProduitMedical()+entry.getValue());
                                                produitMedicalRepository.save(produit);
                                                commande.getProduitsCommande().put(produit.getNomProduitMedical(),entry.getValue());
                                        }
                                }
                                commande.setPrixCommande(commande.getPrixCommande() + produit.getPrixProduitMedical() * entry.getValue());
                        }
                        commandeRepository.save(commande);
                        panier.clear();
                        utilisateurRepository.save(utilisateur);
                        rabbitMQProducer.envoieCommande(commande);
                }
                else {
                        throw new UtilisateurInexistantException();
                }

        }

        @Override
        public void ajouterUtilisateur(Utilisateur utilisateur) throws UtilisateurDejaExistantException {
                if (utilisateurRepository.existsByEmailUtilisateur(utilisateur.getEmailUtilisateur())) {
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
        public void ajouterProduitFournisseur(int idFournisseur, int idProduit) throws FournisseurInexistantException, ProduitDejaDansCatalogueException, ProduitInexistantException {
                if (produitMedicalRepository.existsByIdProduitMedical(idProduit)) {
                        if (fournisseurRepository.existsByIdFournisseur(idFournisseur)) {
                                Fournisseur fournisseur = fournisseurRepository.findByIdFournisseur(idFournisseur);
                                if (fournisseurRepository.existsByCatalogueFournisseur(idProduit)) {
                                        throw new ProduitDejaDansCatalogueException();
                                }
                                ProduitMedical produitMedical = produitMedicalRepository.findByIdProduitMedical(idProduit);
                                fournisseur.getCatalogueFournisseur().put(idProduit, produitMedical.getNomProduitMedical());
                                fournisseurRepository.save(fournisseur);
                        } else {
                                throw new FournisseurInexistantException();
                        }
                }else {
                throw new ProduitInexistantException();
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
        public void ajouterProduitPanier(int idUtilisateur, int idProduit,int quantite) throws UtilisateurInexistantException, ProduitInexistantException {
                if (utilisateurRepository.existsUtilisateurByIdUtilisateur(idUtilisateur))
                {
                        if (!produitMedicalRepository.existsByIdProduitMedical(idProduit))
                        {
                                throw new ProduitInexistantException();
                        }
                        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByIdUtilisateur(idUtilisateur);
                        Map<Integer, Integer> panier = utilisateur.getPanierUtilisateur();
                        if (panier.containsKey(idProduit)) {
                                panier.put(idProduit, panier.get(idProduit) + quantite);
                                utilisateurRepository.save(utilisateur);
                        } else {
                                panier.put(idProduit, quantite);
                                utilisateurRepository.save(utilisateur);
                        }
                }
                else {
                        throw new UtilisateurInexistantException();
                }
        }


        @Override
        public void supprimerProduitPanier(int idUtilisateur, int idProduit) throws ProduitInexistantException {
                System.out.println(idUtilisateur);
                System.out.println(idProduit);
                if (utilisateurRepository.existsUtilisateurByIdUtilisateur(idUtilisateur)) {
                        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByIdUtilisateur(idUtilisateur);
                        System.out.println(utilisateur.toString());

                        Map<Integer, Integer> panier = utilisateur.getPanierUtilisateur();
                        System.out.println(panier);
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
        public void supprimerProduitFromCatalogue(int idFournisseur, int idProduit ) throws FournisseurInexistantException {
                if (fournisseurRepository.existsByIdFournisseur(idFournisseur)) {
                        Fournisseur fournisseur = fournisseurRepository.findByIdFournisseur(idFournisseur);
                        fournisseur.deleteProduit(idProduit);
                        fournisseurRepository.save(fournisseur);

                } else {
                        throw new FournisseurInexistantException();
                }
        }


        @Override
        public void modifierFournisseur(int idFournisseur , Map<String,Object> attributsAModifier) throws FournisseurInexistantException,IllegalArgumentException
        {
                if (!fournisseurRepository.existsByIdFournisseur(idFournisseur))
                {
                        throw new FournisseurInexistantException();
                }
                Fournisseur fournisseur = fournisseurRepository.findByIdFournisseur(idFournisseur);
                for (Map.Entry<String,Object> attributs : attributsAModifier.entrySet())
                {
                        switch (attributs.getKey()) {
                                case "nomFournisseur" ->
                                        fournisseur.setNomFournisseur((String) attributs.getValue());

                                case "adresseFournisseur" ->
                                        fournisseur.setAdresseFournisseur((String) attributs.getValue());
                                case "telephoneFournisseur" ->
                                        fournisseur.setTelephoneFournisseur((String) attributs.getValue());
                                default -> throw new IllegalArgumentException("Attribut inconnu");
                        }
                }
                fournisseurRepository.save(fournisseur);
        }
        @Override
        public void modifierProduit(int idFournisseur , Map<String,Object> attributsAModifier) throws ProduitInexistantException {
                if (!produitMedicalRepository.existsByIdProduitMedical(idFournisseur))
                {
                        throw new ProduitInexistantException();
                }
                ProduitMedical produitMedical = produitMedicalRepository.findByIdProduitMedical(idFournisseur);
                for (Map.Entry<String,Object> attributs : attributsAModifier.entrySet())
                {
                        switch (attributs.getKey()) {
                                case "nomProduitMedical" ->
                                        produitMedical.setNomProduitMedical((String) attributs.getValue());
                                case "prixProduitMedical" ->
                                        produitMedical.setPrixProduitMedical((double) attributs.getValue());
                                case "descriptionProduitMedical" ->
                                        produitMedical.setDescriptionProduitMedical((String) attributs.getValue());
                                default -> throw new IllegalArgumentException("Attribut inconnu");
                        }
                }
                produitMedicalRepository.save(produitMedical);
        }

        @Override
        public void modifierQuantiteProduitMedical(FactureDTO factureDTO) throws ProduitInexistantException, ProduitNonDisponibleException {
                for (Map.Entry<String,Integer> entry : factureDTO.getListeProduits().entrySet())
                {
                        if (produitMedicalRepository.existsByNomProduitMedical(entry.getKey()))
                        {
                                ProduitMedical produitMedical = produitMedicalRepository.findByNomProduitMedical(entry.getKey());
                                if (produitMedical.getStockProduitMedical() < entry.getValue())
                                {
                                        throw new ProduitNonDisponibleException();
                                }
                                produitMedical.setStockProduitMedical(produitMedical.getStockProduitMedical() - entry.getValue());
                                produitMedicalRepository.save(produitMedical);
                                factureDTO.setCoutDuPatient(factureDTO.getCoutDuPatient() + (produitMedical.getPrixProduitMedical() * entry.getValue()));
                                if (produitMedical.getStockProduitMedical() < 6)
                                {
                                        rabbitMQProducer.envoieNotificationStockBas(produitMedical.getNomProduitMedical());
                                }

                        } else {
                                throw new ProduitInexistantException();
                        }
                }
                rabbitMQProducer.envoieFacturePatient(factureDTO);
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
        public ProduitMedical getProduitMedicaleById(int idProduit) throws ProduitInexistantException {
                if (produitMedicalRepository.existsByIdProduitMedical(idProduit))
                        return produitMedicalRepository.findByIdProduitMedical(idProduit);
                else
                        throw new ProduitInexistantException();

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
        public String getAllProduitsFromPanier(int idUtilisateur) throws UtilisateurInexistantException
        {
                if (utilisateurRepository.existsUtilisateurByIdUtilisateur(idUtilisateur))
                {
                        Map<Integer,Integer> panier = utilisateurRepository.findUtilisateurByIdUtilisateur(idUtilisateur).getPanierUtilisateur();
                        List<String> sortie = new ArrayList<>();
                        for (Integer idProduit: panier.keySet())
                        {
                              sortie.add(produitMedicalRepository.findByIdProduitMedical(idProduit).getNomProduitMedical()+" : "+panier.get(idProduit));
                        }
                        return sortie.toString();
                }
                else{
                        throw new UtilisateurInexistantException();
                }

        }





}
