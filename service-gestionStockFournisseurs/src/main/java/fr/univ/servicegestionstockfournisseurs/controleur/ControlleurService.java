package fr.univ.servicegestionstockfournisseurs.controleur;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import fr.univ.servicegestionstockfournisseurs.service.FacadeServiceGestionStock;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.FournisseurDejaExistantException;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.FournisseurInexistantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gestionnaire")
public class ControlleurService {

    @Autowired
    FacadeServiceGestionStock facadeServiceGestionStock;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Fournisseur applyPatchToCustomer(
            JsonPatch patch, Fournisseur targetFournisseur) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetFournisseur, JsonNode.class));
        return objectMapper.treeToValue(patched, Customer.class);
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> qui(Authentication authentication) {
        return ResponseEntity.ok(authentication.getName());
    }

    @PostMapping(value = "/passerCommande")
    public ResponseEntity<String> passerCommande(Authentication authentication)
    {
        String identifiant = authentication.getName();
        facadeServiceGestionStock.passerCommande();
        return ResponseEntity.ok("Commande passée");
    }

    @PostMapping(value = "/ajouterFournisseur")
    public ResponseEntity<String> addNewFournisseur(@RequestBody Fournisseur fournisseur ,Authentication authentication) throws FournisseurDejaExistantException
    {
        String identifiant = authentication.getName();
        try {
            facadeServiceGestionStock.ajouterFournisseur(fournisseur);
            return ResponseEntity.ok("Fournisseur ajouté");
        } catch (FournisseurDejaExistantException e) {
            facadeServiceGestionStock.ajouterFournisseur(fournisseur);
            return ResponseEntity.ok("Fournisseur déjà existant");
        }
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable int id, @RequestBody JsonPatch patch) {
        try {
            Fournisseur fournisseur = facadeServiceGestionStock.getFournisseur(id);
            Fournisseur fournisseurPatched = applyPatchToCustomer(patch, fournisseur);
            facadeServiceGestionStock.modifierFournisseur(fournisseurPatched);
            return ResponseEntity.ok(fournisseurPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }catch (FournisseurInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }








}
