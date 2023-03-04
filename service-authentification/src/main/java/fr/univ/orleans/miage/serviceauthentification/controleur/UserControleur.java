package fr.univ.orleans.miage.serviceauthentification.controleur;

import fr.univ.orleans.miage.serviceauthentification.facade.FacadeUser;
import fr.univ.orleans.miage.serviceauthentification.facade.exceptions.UserDejaExistantException;
import fr.univ.orleans.miage.serviceauthentification.facade.exceptions.UserInexistantException;
import fr.univ.orleans.miage.serviceauthentification.modele.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.function.Function;

@RestController
@RequestMapping(value = "/api/v1/authent", produces = MediaType.APPLICATION_JSON_VALUE)
@EnableWebSecurity
public class UserControleur {

    // Token prefix pour le header de la réponse HTTP (Bearer)
    private static final Object TOKEN_PREFIX = "Bearer";

    @Autowired
    FacadeUser facadeUser;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    Function<User, String> genereToken;


    record AuthDTO(@Size(min=3,max=128) String login, @Size(min=4,max=128) String password) {};

    record UserDTO(@Email String email, @Size(min=4,max=128) String password) {};


    @PostMapping(value = "/inscription", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> inscription(@Valid @RequestBody UserDTO userDTO) {
        try {
            String encodedPassword = passwordEncoder.encode(userDTO.password());
            User u = facadeUser.inscription(userDTO.email(), encodedPassword);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{email}")
                    .buildAndExpand(userDTO.email()).toUri();
            return ResponseEntity.created(location).header(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX+genereToken.apply(u)).build();
        } catch (UserDejaExistantException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email "+userDTO.email()+" déjà pris");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody AuthDTO userDTO) {
        User u = null;
        try {
            u = facadeUser.getUserByEmail(userDTO.login());
        } catch (UserInexistantException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (passwordEncoder.matches(userDTO.password(), u.getMotDePasse())) {
            String token = genereToken.apply(u);
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,"Bearer "+token).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping( "/inscription/{email}")
    @PreAuthorize("#email == authentication.name")
    public ResponseEntity<User> get(@PathVariable String email) {
        User u = null;
        try {
            u = facadeUser.getUserByEmail(email);
        } catch (UserInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(u);
    }

    @DeleteMapping(value = "/inscription/{email}")
    @PreAuthorize("#email == authentication.name")
    public ResponseEntity<String> desinscription(@PathVariable String email) {
        try {
            this.facadeUser.desincription(email);
        } catch (UserInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mauvaises informations transmises !");
        }
        return ResponseEntity.noContent().build();
    }

}
