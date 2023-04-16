package fr.univ.orleans.miage.serviceauthentification;


import fr.univ.orleans.miage.serviceauthentification.service.ConsulService;
import fr.univ.orleans.miage.serviceauthentification.service.UtilisateurService;
import fr.univ.orleans.miage.serviceauthentification.service.VaultService;
import fr.univ.orleans.miage.serviceauthentification.service.exceptions.CompteDejaActiveException;
import fr.univ.orleans.miage.serviceauthentification.service.exceptions.TokenExpirationException;
import fr.univ.orleans.miage.serviceauthentification.service.exceptions.UtilisateurDejaExistantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class InitUtilisateursRunner implements CommandLineRunner {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    ConsulService consulService;

    @Autowired
    VaultService vaultService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {

        try {
            consulService.storePublicKey();
            consulService.storePublicKeyDotnet();

            vaultService.storeKeyPair();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            // Création du compte admin
            String admin = utilisateurService.inscriptionConfirmation("admin@hopital.fr", passwordEncoder.encode("admin"));
            utilisateurService.confirmationCompte(admin);

        } catch (UtilisateurDejaExistantException | CompteDejaActiveException | TokenExpirationException e){
            e.getMessage();
        }

    }
}
