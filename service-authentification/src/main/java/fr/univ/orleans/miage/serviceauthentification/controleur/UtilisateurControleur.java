package fr.univ.orleans.miage.serviceauthentification.controleur;

import fr.univ.orleans.miage.serviceauthentification.dto.UserDTO;
import fr.univ.orleans.miage.serviceauthentification.facade.FacadeUtilisateur;
import fr.univ.orleans.miage.serviceauthentification.facade.exceptions.*;
import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.function.Function;

/**cd
 * Controleur REST pour l'authentification et la gestion des comptes utilisateurs du microservice.
 * @version : 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@EnableWebSecurity
@EnableMethodSecurity
public class UtilisateurControleur {

    /**
     *  Token prefix pour le header de la réponse HTTP (Bearer)
     */
    private static final Object TOKEN_PREFIX = "Bearer";

    @Autowired
    FacadeUtilisateur facadeUser;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Permet de générer un token JWT à partir d'un utilisateur
     */
    @Autowired
    Function<Utilisateur, String> genereToken;

    /**
     * Permet à un utilisateur de créer un compte
     * @param userDTO
     */
    @PostMapping(value = "/inscription")
    public ResponseEntity<String> inscription(@Valid @RequestBody UserDTO userDTO) {
        try {
            String encodedPassword = passwordEncoder.encode(userDTO.password());
            Utilisateur u = facadeUser.inscription(userDTO.email(), encodedPassword);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{email}")
                    .buildAndExpand(userDTO.email()).toUri();
            return ResponseEntity
                    .created(location)
                    .header(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX+genereToken.apply(u)).build();
        } catch (UtilisateurDejaExistantException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email "+userDTO.email()+" déjà pris");
        }
    }

    /**
     * Permet à un utilisateur de se connecter
     * @param userDTO
     */
    @PostMapping("/connexion")
    public ResponseEntity login(@Valid @RequestBody UserDTO userDTO) {
        Utilisateur u = null;
        try {
            u = facadeUser.getUtilisateurByEmail(userDTO.email());
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (passwordEncoder.matches(userDTO.password(), u.getPassword())) {
            String token = genereToken.apply(u);
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,"Bearer "+token).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Permet à un utilisateur de récupérer ses informations
     * @param email
     */
    @GetMapping( "/utilisateurs/{email}")
    @PreAuthorize("#email == authentication.name")
    public ResponseEntity<Utilisateur> getCompteUtilisateur(Authentication authentication, @PathVariable String email) {
        Utilisateur u = null;
        String sv =  authentication.getName();
        try {
            u = facadeUser.getUtilisateurByEmail(email);
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(u);
    }

    /**
     * Permet à un utilisateur de se désinscrire
     * @param email
     */
    @DeleteMapping(value = "/utilisateurs/{email}")
    @PreAuthorize("#email == authentication.name")
    public ResponseEntity<String> desinscription(@PathVariable String email, Authentication authentication) {

        String emailAuth = authentication.getName();

        if (!email.equals(emailAuth)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vous ne pouvez pas supprimer un compte qui n'est pas le votre !");
        }
        try {
            this.facadeUser.desincription(email);
            return ResponseEntity.ok("Le compte utilisateur a été supprimé avec succès.");
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mauvaises informations transmises !");
        }
    }

    /**
     * Consultation de tous les comptes utilisateurs
     */
    @GetMapping("/utilisateurs")
    @PreAuthorize("hasAuthority('SCOPE_MEDECIN') or hasAuthority('SCOPE_PATIENT')")
    public ResponseEntity<Collection<Utilisateur>> getUtilisateurs() {
        try {
            return ResponseEntity.ok(facadeUser.getAllUtilisateurs());
        } catch (DonneesIntrouvablesException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping ("/utilisateurs/type")
    @PreAuthorize("hasAuthority('SCOPE_MEDECIN') or hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<Collection<Utilisateur>> getUtilisateursByRole(@RequestParam String role) {
        try {
            return ResponseEntity.ok(facadeUser.getUtilisateursByRole(role));
        } catch (InformationsFourniesIncorrectesException | RoleInvalideException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } catch (DonneesIntrouvablesException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping ("/utilisateurs/mot-de-passe")
    @PreAuthorize("isAuthenticated() and #userDTO.email == authentication.name")
    public ResponseEntity<String> modifierPassword(Authentication authentication, @RequestBody UserDTO userDTO) {

        String email = authentication.getName();
        String encodedPassword = passwordEncoder.encode(userDTO.password());
        try {
            this.facadeUser.modifierMotDePasse(email,encodedPassword);
            return ResponseEntity.ok("Le mot de passe a été modifié avec succès.");
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
