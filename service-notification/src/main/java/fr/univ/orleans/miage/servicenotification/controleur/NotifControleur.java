package fr.univ.orleans.miage.servicenotification.controleur;

import fr.univ.orleans.miage.servicenotification.modele.Notif;
import fr.univ.orleans.miage.servicenotification.service.NotifService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notif/push/")
public class NotifControleur {

    private final NotifService notifService;

    public NotifControleur(NotifService notifService) {
        this.notifService = notifService;
    }


    /**
     * Envoie une notif et l'enregistre dans la base de données Postgres
     * @param notif à envoyer
     * @return ResponseEntity contenant le notif http
     */
    @Operation(summary = "Envoie une notification")
    @PostMapping("/send-notif")
    public ResponseEntity<String> sendNotifPush(@RequestBody Notif notif) {
        try {
            this.notifService.envoyerNotifPush(notif.getToken(), notif.getTitre(), notif.getMessage());
            return ResponseEntity.status(HttpStatus.CREATED).body("Notif envoyé et enregistré avec succès!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur survenue lors de l'envoi : " + e.getMessage());
        }
    }


    /**
     * Récupère toutes les notifications push envoyées de la base de données Postgres
     * @return Collection des notifications push envoyées
     */
    @Operation(summary = "Récupère toutes les notifications envoyées")
    @GetMapping("/notifs")
    public ResponseEntity<List<Notif>> getAllNotifPush() {
        List<Notif> notifs = this.notifService.getAllNotifPush();
        if (notifs.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(notifs);
        }
    }

}
