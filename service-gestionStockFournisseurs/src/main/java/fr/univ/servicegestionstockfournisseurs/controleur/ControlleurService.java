package fr.univ.servicegestionstockfournisseurs.controleur;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import fr.univ.servicegestionstockfournisseurs.modele.ProduitMedical;
import fr.univ.servicegestionstockfournisseurs.service.FacadeServiceGestionStock;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gestionnaire")
public class ControlleurService {

    @Autowired
    FacadeServiceGestionStock facadeServiceGestionStock;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Fournisseur applyPatchToFournisseur(
            JsonPatch patch, Fournisseur targetFournisseur) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetFournisseur, JsonNode.class));
        return objectMapper.treeToValue(patched, Fournisseur.class);
    }

    private ProduitMedical applyPatchToProduit(
            JsonPatch patch, ProduitMedical targetProduit) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetProduit, JsonNode.class));
        return objectMapper.treeToValue(patched, ProduitMedical.class);
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> qui(Authentication authentication)
    {
        return ResponseEntity.ok(authentication.getName());
    }

    @PostMapping(value = "/passerCommande")
    public ResponseEntity<String> passerCommande(Authentication authentication)
    {
        String identifiant = authentication.getName();
        facadeServiceGestionStock.passerCommande();
        return ResponseEntity.ok("Commande passée");
    }

    @PostMapping(value = "/fournisseurs/ajouterFournisseur")
    public ResponseEntity<String> addNewFournisseur(@RequestBody Fournisseur fournisseur ,Authentication authentication) throws FournisseurDejaExistantException
    {
        String identifiant = authentication.getName();
        try {
            facadeServiceGestionStock.ajouterFournisseur(fournisseur.getNomFournisseur(), fournisseur.getCatalogueFournisseur());
            return ResponseEntity.ok("Fournisseur ajouté");
        } catch (FournisseurDejaExistantException e) {
            return ResponseEntity.badRequest().body("Fournisseur déjà existant");
        }
    }

    @PostMapping(value = "/panier/ajouterProduitPanier")
    public ResponseEntity<String> addProduitPanier(@RequestParam int id, Authentication authentication) throws ProduitDejaExistantException {
        try {
            String identifiant = authentication.getName();
            facadeServiceGestionStock.ajouterProduitPanier(id);
            return ResponseEntity.ok("Produit ajouté au panier");
        }catch (ProduitDejaExistantException e) {
            return ResponseEntity.badRequest().body("Produit déjà existant");
        }
    }

    @DeleteMapping(value = "/panier/supprimerProduitPanier")
    public ResponseEntity<String> deleteProduitPanier(@RequestParam int id, Authentication authentication)
    {
        try {
            String identifiant = authentication.getName();
            facadeServiceGestionStock.supprimerProduitPanier(id);
            return ResponseEntity.ok("Produit supprimé du panier");
        } catch (ProduitInexistantException e) {
            return ResponseEntity.badRequest().body("Produit inexistant dans panier");
        }
    }

    @DeleteMapping(value = "/commandes/{id}")
    public ResponseEntity<String> deleteCommande(@PathVariable int id, Authentication authentication)
    {
        try {
            String identifiant = authentication.getName();
            facadeServiceGestionStock.annulerCommande(id);
            return ResponseEntity.ok("Commande supprimée");
        } catch (CommandeInexistanteException e) {
            return ResponseEntity.badRequest().body("Commande inexistante pour qu'elle soit supprimée");
        }
    }

    @DeleteMapping(value = "/fournisseurs/{id}")
    public ResponseEntity<String> deleteFournisseur(@PathVariable int id, Authentication authentication)
    {
        try {
            String identifiant = authentication.getName();
            facadeServiceGestionStock.supprimerFournisseur(id);
            return ResponseEntity.ok("Fournisseur supprimé");
        } catch (FournisseurInexistantException e) {
            return ResponseEntity.badRequest().body("Fournisseur inexistant pour qu'il soit supprimé");
        }
    }

    @DeleteMapping(value = "/fournisseurs/{idFournisseur}/produits/{idProduit}")
    public ResponseEntity<String> deleteProduitFournisseur(@PathVariable int idFournisseur, @PathVariable int idProduit, Authentication authentication)
    {
        try {
            String identifiant = authentication.getName();
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

    @PatchMapping(path = "/fournisseurs/{idFournisseur}/produits/{idProduit}", consumes = "application/json-patch+json")
    public ResponseEntity<String> updateProduitFournisseur(@PathVariable int idFournisseur, @PathVariable int idProduit, @RequestBody JsonPatch patch) {
        try {
            Fournisseur fournisseur = facadeServiceGestionStock.getFournisseur(idFournisseur);
            ProduitMedical produitToPatch = facadeServiceGestionStock.getProduitFromCatalogueFournisseur(idFournisseur, idProduit);
            ProduitMedical produitPatcher = applyPatchToProduit(patch, produitToPatch);
            facadeServiceGestionStock.modifierProduitFromCatalogue(produitPatcher, idProduit, idFournisseur);
            return ResponseEntity.ok("Produit modifié avec succès");
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }catch (FournisseurInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ProduitInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/commandes/{id}")
    public ResponseEntity<Commande> getCommande(@PathVariable int id, Authentication authentication)
    {
        try {
            String identifiant = authentication.getName();
            Commande commande = facadeServiceGestionStock.getCommande(id);
            return ResponseEntity.ok(commande);
        } catch (CommandeInexistanteException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/fournisseurs/{id}")
    public ResponseEntity<String> getFournisseur(@PathVariable int id, Authentication authentication)
    {
        try {
            String identifiant = authentication.getName();
            Fournisseur fournisseur = facadeServiceGestionStock.getFournisseur(id);
            return ResponseEntity.ok(fournisseur.toString());
        } catch (FournisseurInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/fournisseurs/{id}/produits/{idProduit}")
    public ResponseEntity<String> getProduitFromFournisseur(@PathVariable int id, @PathVariable int idProduit, Authentication authentication)
    {
        try {
            String identifiant = authentication.getName();
            ProduitMedical produit = facadeServiceGestionStock.getProduitFromCatalogueFournisseur(id, idProduit);
            return ResponseEntity.ok(produit.toString());
        } catch (FournisseurInexistantException e) {
            return ResponseEntity.notFound().build();
        } catch (ProduitInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/stock/{idProduit}")
    public ResponseEntity<String> getProduitFromStock(@PathVariable int idProduit, Authentication authentication)
    {
        try {
            String identifiant = authentication.getName();
            ProduitMedical produitMedical = facadeServiceGestionStock.getProduitFromStock(idProduit);
            return ResponseEntity.ok(produitMedical.toString());
        } catch (ProduitInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/fournisseurs/{id}/produits")
    public ResponseEntity<String> getCatalogueFournisseur(@PathVariable int id, Authentication authentication)
    {
        try {
            String identifiant = authentication.getName();
            Map<Integer,ProduitMedical> produits = facadeServiceGestionStock.getCatalogueFournisseur(id);
            return ResponseEntity.ok(produits.toString());
        } catch (FournisseurInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/commandes/{id}/produits")
    public ResponseEntity<String> getProduitsCommande(@PathVariable int id, Authentication authentication)
    {
        try {
            String identifiant = authentication.getName();
            Map<Integer,Integer> produits = facadeServiceGestionStock.getPanierCommande(id);
            return ResponseEntity.ok(facadeServiceGestionStock.getAllProduitsFromPanier(produits).toString());
        } catch (CommandeInexistanteException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/commandes")
    public ResponseEntity<String> getCommandes(Authentication authentication)
    {
        String identifiant = authentication.getName();
        Collection<Commande> commandes = facadeServiceGestionStock.getCommandesDejaPassees();
        return ResponseEntity.ok(commandes.toString());
    }











}
