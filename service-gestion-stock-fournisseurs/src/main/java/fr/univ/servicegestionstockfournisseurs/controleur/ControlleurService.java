package fr.univ.servicegestionstockfournisseurs.controleur;



import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import fr.univ.servicegestionstockfournisseurs.modele.ProduitMedical;
import fr.univ.servicegestionstockfournisseurs.modele.Utilisateur;
import fr.univ.servicegestionstockfournisseurs.producer.RabbitMQProducer;
import fr.univ.servicegestionstockfournisseurs.service.FacadeServiceGestionStock;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    RabbitMQProducer rabbitMQProducer;



    @Operation(summary = "Permet de passer une commande")
    @PostMapping(value = "/utilisateurs/{idUtilisateur}/passerCommande")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<String> passerCommande(@PathVariable int idUtilisateur) throws UtilisateurInexistantException {

        try {
            facadeServiceGestionStock.passerCommande(idUtilisateur);
            return ResponseEntity.ok("Commande passée");
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.badRequest().body("Utilisateur inexistant");
        }

    }

    @Operation(summary = "Permet d'ajouter un secrétaire gestionnaire de stock")
    @PostMapping(value = "/utilisateurs")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<Object> addNewUtilisateur(@RequestBody Utilisateur utilisateur )
    {
        Utilisateur utilisateur1 = null;
        try {
            utilisateur1 = new Utilisateur(utilisateur.getNomUtilisateur(), utilisateur.getPrenomUtilisateur(), utilisateur.getEmailUtilisateur());
            facadeServiceGestionStock.ajouterUtilisateur(utilisateur1);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idUtilisateur}").buildAndExpand(utilisateur1.getIdUtilisateur()).toUri();
            return ResponseEntity.created(location).body(utilisateur1);
        } catch (UtilisateurDejaExistantException e) {
            return ResponseEntity.badRequest().body("Utilisateur déjà existant");
        }
    }

    @Operation(summary = "Permet d'ajouter un produit")
    @PostMapping(value = "/produits")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<Object> addNewProduit(@RequestBody ProduitMedical produitMedical) throws ProduitDejaExistantException
    {
        ProduitMedical produitMedical1 = new ProduitMedical(produitMedical.getNomProduitMedical(), produitMedical.getPrixProduitMedical(),produitMedical.getDescriptionProduitMedical());
        try {
            facadeServiceGestionStock.ajouterProduit(produitMedical1.getNomProduitMedical(), produitMedical1.getPrixProduitMedical(),produitMedical1.getDescriptionProduitMedical());
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idProduit}").buildAndExpand(produitMedical1.getIdProduitMedical()).toUri();
            return ResponseEntity.created(location).body(produitMedical1);
        } catch (ProduitDejaExistantException e) {
            return ResponseEntity.badRequest().body("Produit déjà existant");
        }
    }

    @Operation(summary = "Permet d'ajouter un fournisseur")
    @PostMapping(value = "/fournisseurs")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<Object> addNewFournisseur(@RequestBody Fournisseur fournisseur) throws FournisseurDejaExistantException
    {
        try {
            Fournisseur fournisseur1 = new Fournisseur(fournisseur.getNomFournisseur(), fournisseur.getAdresseFournisseur(), fournisseur.getTelephoneFournisseur());
            facadeServiceGestionStock.ajouterFournisseur(fournisseur1);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idFournisseur}").buildAndExpand(fournisseur1.getIdFournisseur()).toUri();
            return ResponseEntity.created(location).body(fournisseur1);
        } catch (FournisseurDejaExistantException e) {
            return ResponseEntity.badRequest().body("Fournisseur déjà existant");
        }
    }

    @Operation(summary = "Permet d'ajouter un produit au catalogue d'un fournisseur")
    @PostMapping(value = "/fournisseurs/{id}/catalogue")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<String> addProduitFournisseur(@PathVariable int id, @RequestParam int idProduit) throws ProduitDejaExistantException
    {
        try {

            facadeServiceGestionStock.ajouterProduitFournisseur(id, idProduit);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idProduit}").buildAndExpand(idProduit).toUri();

            return ResponseEntity.created(location).body("Produit ajouté au catalogue fournisseur");
        } catch (FournisseurInexistantException e) {
            return ResponseEntity.notFound().build();
        } catch (ProduitDejaDansCatalogueException e) {
            return ResponseEntity.badRequest().body("Produit déjà dans le catalogue");
        }
    }

    @Operation(summary = "Permet d'ajouter un produit au panier")
    @PostMapping(value = "/utilisateurs/{idUtilisateur}/panier")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<String> addProduitPanier(@PathVariable ("idUtilisateur") int idUtilisateur, @RequestParam int idProduit, @RequestParam int quantite)
    {
        try {

            facadeServiceGestionStock.ajouterProduitPanier(idUtilisateur, idProduit, quantite);
            return ResponseEntity.ok("Produit ajouté au panier");
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.notFound().build();
        } catch (ProduitInexistantException e) {
            return ResponseEntity.badRequest().body("Produit inexistant");
        }
    }

    @Operation(summary = "Permet de supprimer un produit du panier")
    @DeleteMapping(value = "/utilisateurs/{idUtilisateur}/panier/{idProduit}")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<String> deleteProduitPanier(@PathVariable ("idProduit") int idProduit,@PathVariable ("idUtilisateur") int idUtilisateur)
    {
        try {
            facadeServiceGestionStock.supprimerProduitPanier(idUtilisateur, idProduit);
            return ResponseEntity.accepted().body("Produit supprimé du panier");
        } catch (ProduitInexistantException e) {
            return ResponseEntity.badRequest().body("Produit inexistant dans panier");
        }
    }

    @Operation(summary = "Permet d'annuler une commande)")
    @DeleteMapping(value = "/commandes/{id}")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<String> deleteCommande(@PathVariable int id)
    {
        try {
            facadeServiceGestionStock.annulerCommande(id);
            return ResponseEntity.accepted().body("Commande supprimée");
        } catch (CommandeInexistanteException e) {
            return ResponseEntity.badRequest().body("Commande inexistante pour qu'elle soit supprimée");
        }
    }

    @Operation(summary = "Permet de supprimer un fournisseur")
    @DeleteMapping(value = "/fournisseurs/{id}")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<String> deleteFournisseur(@PathVariable int id)
    {
        try {
            facadeServiceGestionStock.supprimerFournisseur(id);
            return ResponseEntity.accepted().body("Fournisseur supprimé");
        } catch (FournisseurInexistantException e) {
            return ResponseEntity.badRequest().body("Fournisseur inexistant pour qu'il soit supprimé");
        }
    }

    @Operation(summary = "Permet de supprimer un produit du catalogue d'un fournisseur")
    @DeleteMapping(value = "/fournisseurs/{idFournisseur}/catalogue/{idProduit}")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<String> deleteProduitFournisseur(@PathVariable int idFournisseur, @PathVariable int idProduit)
    {
        try {
            facadeServiceGestionStock.supprimerProduitFromCatalogue(idFournisseur, idProduit);
            return ResponseEntity.accepted().body("Produit supprimé du catalogue fournisseur");
        } catch (ProduitInexistantException e) {
            return ResponseEntity.badRequest().body("Produit inexistant pour qu'il soit supprimé");
        }
    }

    @Operation(summary = "Permet de mettre à jour les informations d'un fournisseur")
    @PatchMapping(path = "/fournisseurs/{id}")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<String> updateFournisseur(@PathVariable int id, @RequestBody Map<String,Object> attributsAModifier ) {
        try {

            facadeServiceGestionStock.modifierFournisseur(id,attributsAModifier);
            return ResponseEntity.accepted().body("Fournisseur modifié");
        } catch (FournisseurInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Permet de mettre à jour les informations d'un produit")
    @PatchMapping(path = "/produits/{idProduit}")
    public ResponseEntity<String> updateProduit(@PathVariable int idProduit, @RequestBody Map<String,Object> attributsAModifier) {
        try {

            facadeServiceGestionStock.modifierProduit( idProduit, attributsAModifier);
            return ResponseEntity.ok("Produit modifié avec succès");
        }  catch (ProduitInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Permet de récupérer une commande")
    @GetMapping(value = "/commandes/{id}")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
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

    @Operation(summary = "Permet de récupérer un fournisseur")
    @GetMapping(value = "/fournisseurs/{id}")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<String> getFournisseur(@PathVariable int id)
    {
        try {
            Fournisseur fournisseur = facadeServiceGestionStock.getFournisseur(id);
            return ResponseEntity.ok(fournisseur.toString());
        } catch (FournisseurInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Permet de récupérer le panier d'un utilisateur")
    @GetMapping(value = "/utilisateurs/{id}/panier")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<String> getPanierUtilisateur(@PathVariable int id)
    {
        try {
            return ResponseEntity.ok(facadeServiceGestionStock.getAllProduitsFromPanier(id));
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Permet de récupérer le stock d'un produit")
    @GetMapping(value = "/produitsMedical/{idProduit}/stock")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
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

    @Operation(summary = "Permet de récupérer le catalogue d'un fournisseur")
    @GetMapping(value = "/fournisseurs/{id}/produits")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
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

    @Operation(summary = "Récupère les produits d'une commande")
    @GetMapping(value = "/commandes/{idCommande}/panier")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<String> getProduitsCommande(@PathVariable int idCommande)
    {
        try {
            //String identifiant = authentication.getName();
            return ResponseEntity.ok(facadeServiceGestionStock.getAllProduitsFromPanier(idCommande));
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Récupère toutes les commandes passées")
    @GetMapping(value = "/commandes")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<String> getCommandes()
    {
        Collection<Commande> commandes = facadeServiceGestionStock.getCommandesDejaPassees();
        return ResponseEntity.ok(commandes.toString());
    }











}
