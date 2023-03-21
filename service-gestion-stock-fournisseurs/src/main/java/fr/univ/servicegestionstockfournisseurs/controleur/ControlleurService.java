package fr.univ.servicegestionstockfournisseurs.controleur;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import fr.univ.servicegestionstockfournisseurs.modele.ProduitMedical;
import fr.univ.servicegestionstockfournisseurs.modele.Utilisateur;
import fr.univ.servicegestionstockfournisseurs.service.FacadeServiceGestionStock;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/gestionnaire", produces = "application/json")
public class ControlleurService {

    @Autowired
    FacadeServiceGestionStock facadeServiceGestionStock;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Fournisseur applyPatchToFournisseur(JsonPatch patch, Fournisseur targetFournisseur) throws JsonPatchException, JsonProcessingException
    {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetFournisseur, JsonNode.class));
        return objectMapper.treeToValue(patched, Fournisseur.class);
    }

    private ProduitMedical applyPatchToProduit(
            JsonPatch patch, ProduitMedical targetProduit) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetProduit, JsonNode.class));
        return objectMapper.treeToValue(patched, ProduitMedical.class);
    }

//    @GetMapping(value = "/")
//    public ResponseEntity<String> qui(Authentication authentication)
//    {
//        return ResponseEntity.ok(authentication.getName());
//    }

    @PostMapping(value = "/utilisateurs/{idUtilisateur}/passerCommande")
    public ResponseEntity<String> passerCommande(@PathVariable int idUtilisateur) throws UtilisateurInexistantException {

            //String identifiant = authentication.getName();
        try {
            facadeServiceGestionStock.passerCommande(idUtilisateur);
            return ResponseEntity.ok("Commande passée");
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.badRequest().body("Utilisateur inexistant");
        }


    }

    @PostMapping(value = "/utilisateurs")
    public ResponseEntity<Object> addNewUtilisateur(@RequestBody Utilisateur utilisateur )
    {
        Utilisateur utilisateur1 = null;

        //String identifiant = authentication.getName();
        try {
            utilisateur1 = new Utilisateur(utilisateur.getNomUtilisateur(), utilisateur.getPrenomUtilisateur(), utilisateur.getEmailUtilisateur());
            facadeServiceGestionStock.ajouterUtilisateur(utilisateur1);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idUtilisateur}").buildAndExpand(utilisateur1.getIdUtilisateur()).toUri();
            return ResponseEntity.created(location).body(utilisateur1);
        } catch (UtilisateurDejaExistantException e) {
            return ResponseEntity.badRequest().body("Utilisateur déjà existant");
        }
    }

    @PostMapping(value = "/produitsMedical")
    public ResponseEntity<Object> addNewProduit(@RequestBody ProduitMedical produitMedical) throws ProduitDejaExistantException
    {
        ProduitMedical produitMedical1 = new ProduitMedical(produitMedical.getNomProduitMedical(), produitMedical.getPrixProduitMedical(),produitMedical.getDescriptionProduitMedical());
        //String identifiant = authentication.getName();
        try {
            facadeServiceGestionStock.ajouterProduit(produitMedical1.getNomProduitMedical(), produitMedical1.getPrixProduitMedical(),produitMedical1.getDescriptionProduitMedical());
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idProduit}").buildAndExpand(produitMedical1.getIdProduitMedical()).toUri();
            return ResponseEntity.created(location).body(produitMedical1);
        } catch (ProduitDejaExistantException e) {
            return ResponseEntity.badRequest().body("Produit déjà existant");
        }
    }

    @PostMapping(value = "/fournisseurs")
    public ResponseEntity<Object> addNewFournisseur(@RequestBody Fournisseur fournisseur) throws FournisseurDejaExistantException
    {
        //String identifiant = authentication.getName();
        try {
            Fournisseur fournisseur1 = new Fournisseur(fournisseur.getNomFournisseur(), fournisseur.getAdresseFournisseur(), fournisseur.getTelephoneFournisseur());
            facadeServiceGestionStock.ajouterFournisseur(fournisseur1);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idFournisseur}").buildAndExpand(fournisseur1.getIdFournisseur()).toUri();
            return ResponseEntity.created(location).body(fournisseur1);
        } catch (FournisseurDejaExistantException e) {
            return ResponseEntity.badRequest().body("Fournisseur déjà existant");
        }
    }

    @PostMapping(value = "/fournisseurs/{id}/catalogue")
    public ResponseEntity<String> addProduitFournisseur(@PathVariable int id, @RequestParam int idProduit) throws ProduitDejaExistantException
    {
        try {
            //String identifiant = authentication.getName();
            ProduitMedical produitMedical = facadeServiceGestionStock.getProduitMedicaleById(idProduit);
            facadeServiceGestionStock.ajouterProduitFournisseur(id, idProduit);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idProduit}").buildAndExpand(idProduit).toUri();

            return ResponseEntity.created(location).body("Produit ajouté au catalogue fournisseur");
        } catch (FournisseurInexistantException e) {
            return ResponseEntity.notFound().build();
        } catch (ProduitDejaDansCatalogueException e) {
            return ResponseEntity.badRequest().body("Produit déjà dans le catalogue");
        } catch (ProduitInexistantException e) {
            return ResponseEntity.badRequest().body("Produit inexistant");
        }
    }

    @PostMapping(value = "/utilisateurs/{idUtilisateur}/panier")
    public ResponseEntity<String> addProduitPanier(@PathVariable ("idUtilisateur") int idUtilisateur, @RequestParam int idProduit, @RequestParam int quantite)
    {
        try {
            //String identifiant = authentication.getName();
            facadeServiceGestionStock.ajouterProduitPanier(idUtilisateur, idProduit, quantite);
            return ResponseEntity.ok("Produit ajouté au panier");
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.notFound().build();
        } catch (ProduitInexistantException e) {
            return ResponseEntity.badRequest().body("Produit inexistant");
        }
    }

    @DeleteMapping(value = "/utilisateurs/{idUtilisateur}/panier/{idProduit}")
    public ResponseEntity<String> deleteProduitPanier(@PathVariable ("idProduit") int idProduit,@PathVariable ("idUtilisateur") int idUtilisateur)
    {
        try {
            //String identifiant = authentication.getName();
            facadeServiceGestionStock.supprimerProduitPanier(idUtilisateur, idProduit);
            return ResponseEntity.ok("Produit supprimé du panier");
        } catch (ProduitInexistantException e) {
            return ResponseEntity.badRequest().body("Produit inexistant dans panier");
        }
    }

    @DeleteMapping(value = "/commandes/{id}")
    public ResponseEntity<String> deleteCommande(@PathVariable int id)
    {
        try {
            //String identifiant = authentication.getName();
            facadeServiceGestionStock.annulerCommande(id);
            return ResponseEntity.ok("Commande supprimée");
        } catch (CommandeInexistanteException e) {
            return ResponseEntity.badRequest().body("Commande inexistante pour qu'elle soit supprimée");
        }
    }

    @DeleteMapping(value = "/fournisseurs/{id}")
    public ResponseEntity<String> deleteFournisseur(@PathVariable int id)
    {
        try {
            //String identifiant = authentication.getName();
            facadeServiceGestionStock.supprimerFournisseur(id);
            return ResponseEntity.ok("Fournisseur supprimé");
        } catch (FournisseurInexistantException e) {
            return ResponseEntity.badRequest().body("Fournisseur inexistant pour qu'il soit supprimé");
        }
    }

    @DeleteMapping(value = "/fournisseurs/{idFournisseur}/produits/{idProduit}")
    public ResponseEntity<String> deleteProduitFournisseur(@PathVariable int idFournisseur, @PathVariable int idProduit)
    {
        try {
            //String identifiant = authentication.getName();
            facadeServiceGestionStock.supprimerProduitFromCatalogue(idFournisseur, idProduit);
            return ResponseEntity.ok("Produit supprimé du fournisseur");
        } catch (ProduitInexistantException e) {
            return ResponseEntity.badRequest().body("Produit inexistant pour qu'il soit supprimé");
        }
    }

    @PatchMapping(path = "/fournisseurs/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable int id, @RequestBody JsonPatch patch) {
        try {
            Fournisseur fournisseur = facadeServiceGestionStock.getFournisseur(id);
            Fournisseur fournisseurPatched = applyPatchToFournisseur(patch, fournisseur);
            facadeServiceGestionStock.modifierFournisseur(fournisseurPatched);
            return ResponseEntity.ok(fournisseurPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }catch (FournisseurInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

//    @PatchMapping(path = "/fournisseurs/{idFournisseur}/produits/{idProduit}", consumes = "application/json-patch+json")
//    public ResponseEntity<String> updateProduitFournisseur(@PathVariable int idFournisseur, @PathVariable int idProduit, @RequestBody JsonPatch patch) {
//        try {
//            Fournisseur fournisseur = facadeServiceGestionStock.getFournisseur(idFournisseur);
//            ProduitMedical produitToPatch = facadeServiceGestionStock.getProduitFromCatalogueFournisseur(idFournisseur, idProduit);
//            ProduitMedical produitPatcher = applyPatchToProduit(patch, produitToPatch);
//            facadeServiceGestionStock.modifierProduitFromCatalogue(produitPatcher, idProduit, idFournisseur);
//            return ResponseEntity.ok("Produit modifié avec succès");
//        } catch (JsonPatchException | JsonProcessingException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }catch (FournisseurInexistantException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } catch (ProduitInexistantException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

    @GetMapping(value = "/commandes/{id}")
    public ResponseEntity<Commande> getCommande(@PathVariable int id)
    {
        try {
            //String identifiant = authentication.getName();
            Commande commande = facadeServiceGestionStock.getCommande(id);
            return ResponseEntity.ok(commande);
        } catch (CommandeInexistanteException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/fournisseurs/{id}")
    public ResponseEntity<String> getFournisseur(@PathVariable int id)
    {
        try {
            //String identifiant = authentication.getName();
            Fournisseur fournisseur = facadeServiceGestionStock.getFournisseur(id);
            return ResponseEntity.ok(fournisseur.toString());
        } catch (FournisseurInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/utilisateurs/{id}/panier")
    public ResponseEntity<String> getPanierUtilisateur(@PathVariable int id)
    {
        try {
            //String identifiant = authentication.getName();
            return ResponseEntity.ok(facadeServiceGestionStock.getAllProduitsFromPanier(id));
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }


//    @GetMapping(value = "/fournisseurs/{id}/produits/{idProduit}")
//    public ResponseEntity<String> getProduitFromFournisseur(@PathVariable int id, @PathVariable int idProduit)
//    {
//        try {
//            //String identifiant = authentication.getName();
//            ProduitMedical produit = facadeServiceGestionStock.getProduitFromCatalogueFournisseur(id, idProduit);
//            return ResponseEntity.ok(produit.toString());
//        } catch (FournisseurInexistantException e) {
//            return ResponseEntity.notFound().build();
//        } catch (ProduitInexistantException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping(value = "/produitsMedical/{idProduit}/stock")
    public ResponseEntity<Integer> getStockProduit(@PathVariable int idProduit)
    {
        try {
            //String identifiant = authentication.getName();
            int nbProduitStock = facadeServiceGestionStock.getStockProduit(idProduit);
            return ResponseEntity.ok(nbProduitStock);
        } catch (ProduitInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/fournisseurs/{id}/produits")
    public ResponseEntity<String> getCatalogueFournisseur(@PathVariable int id)
    {
        try {
            //String identifiant = authentication.getName();
            Map<Integer,String> produits = facadeServiceGestionStock.getCatalogueFournisseur(id);
            return ResponseEntity.ok(produits.toString());
        } catch (FournisseurInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/commandes/{idCommande}/panier")
    public ResponseEntity<String> getProduitsCommande(@PathVariable int idCommande)
    {
        try {
            //String identifiant = authentication.getName();
            return ResponseEntity.ok(facadeServiceGestionStock.getAllProduitsFromPanier(idCommande));
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/commandes")
    public ResponseEntity<String> getCommandes()
    {
        //String identifiant = authentication.getName();
        Collection<Commande> commandes = facadeServiceGestionStock.getCommandesDejaPassees();
        return ResponseEntity.ok(commandes.toString());
    }











}
