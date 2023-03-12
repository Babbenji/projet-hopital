package fr.univ.orleans.miage.servicenotification.controleur;

import fr.univ.orleans.miage.servicenotification.dto.EmailDto;
import fr.univ.orleans.miage.servicenotification.modele.Email;
import fr.univ.orleans.miage.servicenotification.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notif")
public class EmailControleur {

    private final EmailService emailService;

    public EmailControleur(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Récupère un email envoyé de la base de données Postgres
     * @param emailDto objet contenant les informations sur l'email à envoyer
     * @return ResponseEntity contenant le message http
     */
    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody @Valid EmailDto emailDto) {
        try {
            Email email = new Email();
            BeanUtils.copyProperties(emailDto, email);
            this.emailService.envoyerEmail(email);
            return ResponseEntity.status(HttpStatus.CREATED).body("Email envoyé et enregistré avec succès!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi du mail : " + e.getMessage());
        }
    }


    /**
     * Récupère tous les emails envoyés de la base de données Postgres
     * @return Collection d'emails envoyés
     */
    @GetMapping("/emails")
    public ResponseEntity<List<Email>> getAllEmails() {
        List<Email> emails = this.emailService.getAllEmails();
        if (emails.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(emails);
        }
    }


}
