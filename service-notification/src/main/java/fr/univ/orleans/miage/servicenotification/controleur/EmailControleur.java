package fr.univ.orleans.miage.servicenotification.controleur;

import fr.univ.orleans.miage.servicenotification.modele.EmailMessage;
import fr.univ.orleans.miage.servicenotification.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class EmailControleur {

    private final EmailService emailService;

    public EmailControleur(EmailService emailService) {
        this.emailService = emailService;
    }


    @PostMapping("/email")
    public ResponseEntity sendEmail(@RequestBody EmailMessage emailMessage) {
        this.emailService.sendEmail(emailMessage.getTo(), emailMessage.getSubject(), emailMessage.getBody());
        return ResponseEntity.ok("Email sent successfully!");
    }
}
