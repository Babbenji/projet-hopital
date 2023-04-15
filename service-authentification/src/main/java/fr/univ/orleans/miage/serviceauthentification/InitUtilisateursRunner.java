package fr.univ.orleans.miage.serviceauthentification;

import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;
import fr.univ.orleans.miage.serviceauthentification.service.ConsulService;
import fr.univ.orleans.miage.serviceauthentification.service.UtilisateurService;
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
    PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {

        try {
            consulService.storePublicKey();
            consulService.storePublicKeyDotnet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            // Créer un compte admin
            String admin = utilisateurService.inscriptionConfirmation("admin@hopital.fr", passwordEncoder.encode("admin"));
            utilisateurService.confirmationCompte(admin);

            // Créer un compte médecin
            String medecin = utilisateurService.inscriptionConfirmation("medecin@hopital-medecin.fr", passwordEncoder.encode("mdp"));
            utilisateurService.confirmationCompte(medecin);

            // Créer un compte comptable
            String comptable = utilisateurService.inscriptionConfirmation("comptable@hopital-comptable.fr", passwordEncoder.encode("mdp"));
            utilisateurService.confirmationCompte(comptable);

            // Créer un compte secrétaire
            String secretaire = utilisateurService.inscriptionConfirmation("secretaire@hopital-secretaire.fr", passwordEncoder.encode("mdp"));
            utilisateurService.confirmationCompte(secretaire);

            // Créer des comptes patients
            for (int i = 1; i <= 3; i++) {
                Utilisateur patient = utilisateurService.inscriptionSansConfirmation("patient" + i + "@example.com", passwordEncoder.encode("mdp"));
            }

            System.out.println("Comptes créés avec succès !");

        } catch (UtilisateurDejaExistantException | CompteDejaActiveException | TokenExpirationException e){
            System.out.println("Erreur lors de la création des comptes : " + e.getMessage());
        }

    }
}
