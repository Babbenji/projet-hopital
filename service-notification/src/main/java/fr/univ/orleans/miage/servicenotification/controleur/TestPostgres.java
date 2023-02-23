
package fr.univ.orleans.miage.servicenotification.controleur;

import fr.univ.orleans.miage.servicenotification.modele.Email;
import fr.univ.orleans.miage.servicenotification.repository.EmailRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestPostgres {

    private final EmailRepository emailRepository;

    public TestPostgres(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }


    @GetMapping("/email")
    public ResponseEntity getAllEmails() {
        return ResponseEntity.ok(this.emailRepository.findAll());
    }

    @PostMapping("/email")
    public ResponseEntity addEmail(@RequestBody Email email) {
        this.emailRepository.save(email);
        return ResponseEntity.ok("Email added successfully!");
    }

//    @PutMapping("/email")
//    public ResponseEntity updateEmail(@RequestBody EmailMessage emailMessage) {
//        this.emailMessageRepository.save(emailMessage);
//        return ResponseEntity.ok("Email updated successfully!");
//    }

//    @PostMapping("/email")
//    public ResponseEntity sendEmail(@RequestBody EmailMessage emailMessage) {
//        this.emailService.sendEmail(emailMessage.getTo(), emailMessage.getObjet(), emailMessage.getContenu());
//        return ResponseEntity.ok("Email sent successfully!");
//    }
}
