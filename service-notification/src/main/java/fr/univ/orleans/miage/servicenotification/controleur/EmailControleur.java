package fr.univ.orleans.miage.servicenotification.controleur;

import fr.univ.orleans.miage.servicenotification.modele.Email;
import fr.univ.orleans.miage.servicenotification.repository.EmailRepository;
import fr.univ.orleans.miage.servicenotification.service.EmailService;
import fr.univ.orleans.miage.servicenotification.service.EmailServicePostgres;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
public class EmailControleur {

    private final EmailServicePostgres emailServicePostgres;

    public EmailControleur(EmailServicePostgres emailServicePostgres) {
        this.emailServicePostgres = emailServicePostgres;
    }

    /**
     * Envoie un email et l'enregistre dans la base de données Postgres
     * @param email
     */
    @PostMapping("/email")
    public ResponseEntity sendAndSaveEmail(@RequestBody Email email) {
        this.emailServicePostgres.envoyerEmail(email.getDestinataire(), email.getObjet(), email.getContenu());
        return ResponseEntity.ok("Email envoyé et enregistré avec succès!");
    }

}
