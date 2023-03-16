package fr.univ.orleans.miage.serviceauthentification;

import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;
import fr.univ.orleans.miage.serviceauthentification.service.UtilisateurService;
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
    PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {

        try {
            // Créer un compte admin
             Utilisateur admin = utilisateurService.inscription("admin@hopital.fr", passwordEncoder.encode("admin"));

            // Créer un compte médecin
            Utilisateur medecin = utilisateurService.inscription("medecin@hopital-medecin.fr", passwordEncoder.encode("mdp"));

            // Créer un compte comptable
            Utilisateur comptable = utilisateurService.inscription("comptable@hopital-comptable.fr", passwordEncoder.encode("mdp"));

            // Créer un compte secrétaire
            Utilisateur secretaire = utilisateurService.inscription("secretaire@hopital-secretaire.fr", passwordEncoder.encode("mdp"));

            // Créer des comptes patients
            for (int i = 1; i <= 3; i++) {
                Utilisateur patient = utilisateurService.inscription("patient" + i + "@example.com", passwordEncoder.encode("mdp"));
            }

            System.out.println("Comptes créés avec succès !");

        } catch (UtilisateurDejaExistantException e) {
            System.out.println("Erreur lors de la création des comptes : " + e.getMessage());
        }


    }
}
