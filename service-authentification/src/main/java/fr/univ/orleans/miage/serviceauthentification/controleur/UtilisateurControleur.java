package fr.univ.orleans.miage.serviceauthentification.controleur;

import fr.univ.orleans.miage.serviceauthentification.dto.UserDTO;
import fr.univ.orleans.miage.serviceauthentification.service.UtilisateurService;
import fr.univ.orleans.miage.serviceauthentification.service.exceptions.*;
import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;
import fr.univ.orleans.miage.serviceauthentification.token.TokenConfirmation;
import fr.univ.orleans.miage.serviceauthentification.token.TokenConfirmationService;
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
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.function.Function;

/**
 * Controleur REST pour l'authentification et la gestion des comptes utilisateurs du microservice.
 * @version : 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@EnableWebSecurity
@EnableMethodSecurity
public class UtilisateurControleur {

    /**
     *  Token prefix pour le header de la réponse HTTP (Bearer) dans le cas de l'authentification
     */
    private static final Object TOKEN_PREFIX = "Bearer";

    /**
     * Fournit les services pour la gestion des comptes utilisateurs du microservice (inscription, connexion, etc.)
     */
    @Autowired
    UtilisateurService utilisateurService;

    /**
     * Fournit les services pour la gestion des tokens de confirmation des comptes utilisateurs lors de l'inscription
     */
    @Autowired
    TokenConfirmationService tokenConfirmationService;

    /**
     * Permet de crypter les mots de passe avant de les stocker dans la base de données
     */
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
    @PostMapping(value = "/inscription-v0")
    public ResponseEntity<String> inscription(@Valid @RequestBody UserDTO userDTO) {
        try {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            Utilisateur u = utilisateurService.inscription(userDTO.getEmail(), encodedPassword);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{email}")
                    .buildAndExpand(userDTO.getEmail()).toUri();
            return ResponseEntity
                    .created(location)
                    .header(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX+" "+genereToken.apply(u))
                    .body("Utilisateur créé avec succès");
//                    .build();
        } catch (UtilisateurDejaExistantException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email "+userDTO.getEmail()+" déjà pris");
        }
    }

    @PostMapping(value = "/inscription")
    public ResponseEntity<String> inscriptionConfirmation(@Valid @RequestBody UserDTO userDTO) {
        try {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            String u = utilisateurService.inscriptionConfirmation(userDTO.getEmail(), encodedPassword);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{email}")
                    .buildAndExpand(userDTO.getEmail()).toUri();
            return ResponseEntity
                    .created(location)
                    .body("Utilisateur créé avec succès " + u);
        } catch (UtilisateurDejaExistantException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email "+userDTO.getEmail()+" déjà pris");
        }
    }

    @GetMapping("/confirmation-compte")
    public ResponseEntity<String> confirmationCompte(@RequestParam("token") String token) {
        try {
            this.utilisateurService.confirmationCompte(token);
            return ResponseEntity.ok().body("Compte confirmé avec succès");
        } catch (CompteDejaActiveException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le compte a déjà été activé");
        } catch (TokenExpirationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token de confirmation invalide ou a expiré");
        }
    }

    /**
     * Permet à un utilisateur de se connecter
     * @param userDTO informations de connexion
     */
    @PostMapping("/connexion")
    public ResponseEntity login(@Valid @RequestBody UserDTO userDTO) {
        Utilisateur u = null;
        try {
            u = utilisateurService.getUtilisateurByEmail(userDTO.getEmail());
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (!u.isCompteActive()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Le compte n'est pas encore activé.");
        }
        if (passwordEncoder.matches(userDTO.getPassword(), u.getPassword())) {
            String token = genereToken.apply(u);
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,"Bearer "+token).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Permet à un utilisateur de récupérer ses informations
     */
    @GetMapping( "/utilisateurs/{email}")
    @PreAuthorize("#email == authentication.name")
    public ResponseEntity<Utilisateur> getCompteUtilisateur(Authentication authentication, @PathVariable String email) {
        Utilisateur utilisateur = null;
        String emailUserConnecte =  authentication.getName();
        if (!email.equals(emailUserConnecte)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        try {
            utilisateur = utilisateurService.getUtilisateurByEmail(email);
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(utilisateur);
    }

    /**
     * Permet à un utilisateur de se désinscrire
     */
    @DeleteMapping(value = "/utilisateurs/{email}")
    @PreAuthorize("#email == authentication.name")
    public ResponseEntity<String> desinscription(@PathVariable String email, Authentication authentication) {

        String emailAuth = authentication.getName();

        if (!email.equals(emailAuth)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vous ne pouvez pas supprimer un compte qui n'est pas le votre !");
        }
        try {
            this.utilisateurService.desincription(email);
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
            return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
        } catch (DonneesIntrouvablesException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping ("/utilisateurs/type")
    @PreAuthorize("hasAuthority('SCOPE_MEDECIN') or hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<Collection<Utilisateur>> getUtilisateursByRole(@RequestParam String role) {
        try {
            return ResponseEntity.ok(utilisateurService.getUtilisateursByRole(role));
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
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        try {
            this.utilisateurService.modifierMotDePasse(email,encodedPassword);
            return ResponseEntity.ok("Le mot de passe a été modifié avec succès.");
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
