package fr.univ.orleans.miage.servicenotification.controleur;

import fr.univ.orleans.miage.servicenotification.modele.Email;
import fr.univ.orleans.miage.servicenotification.modele.Push;
import fr.univ.orleans.miage.servicenotification.service.EmailService;
import fr.univ.orleans.miage.servicenotification.service.PushService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notif")
public class NotifControleur {

    private final EmailService emailService;

    private final PushService pushService;

    public NotifControleur(EmailService emailService, PushService pushService) {
        this.emailService = emailService;
        this.pushService = pushService;
    }

    /**
     * Récupère un email envoyé de la base de données Postgres
     * @param email à envoyer
     * @return ResponseEntity contenant le message http
     */
    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody Email email) {
        try {
            this.emailService.envoyerEmail(email.getDestinataire(), email.getObjet(), email.getContenu());
            return ResponseEntity.status(HttpStatus.CREATED).body("Email envoyé et enregistré avec succès!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi du mail : " + e.getMessage());
        }
    }

    /**
     * Envoie un push et l'enregistre dans la base de données Postgres
     * @param push à envoyer
     * @return ResponseEntity contenant le push http
     */
    @PostMapping("/send-push")
    public ResponseEntity<String> sendNotifPush(@RequestBody Push push) {
        try {
            this.pushService.envoyerNotifPush(push.getToken(), push.getTitre(), push.getMessage());
            return ResponseEntity.status(HttpStatus.CREATED).body("Push envoyé et enregistré avec succès!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur survenue lors de l'envoi : " + e.getMessage());
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


    /**
     * Récupère toutes les notifications push envoyées de la base de données Postgres
     * @return Collection des notifications push envoyées
     */
    @GetMapping("/pushes")
    public ResponseEntity<List<Push>> getAllNotifPush() {
        List<Push> pushes = this.pushService.getAllNotifPush();
        if (pushes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pushes);
        }
    }

}
