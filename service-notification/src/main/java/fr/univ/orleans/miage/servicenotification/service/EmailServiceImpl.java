package fr.univ.orleans.miage.servicenotification.service;

import fr.univ.orleans.miage.servicenotification.modele.Email;
import fr.univ.orleans.miage.servicenotification.repository.EmailRepository;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(EmailRepository emailRepository, JavaMailSender javaMailSender) {
        this.emailRepository = emailRepository;
        this.javaMailSender = javaMailSender;
    }

    /**
     * Envoie un email à un destinataire et l'enregistre dans la base de données Postgres
     * @param destinataire
     * @param objet
     * @param contenu
     */
    @Override
    public void envoyerEmail(String destinataire, String objet, String contenu) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply.hopital@gmail.com");
        message.setTo(destinataire);
        message.setSubject(objet);
        message.setText(contenu);

        this.javaMailSender.send(message);
        this.emailRepository.save(new Email(destinataire, objet, contenu));
    }

    public List<Email> getAllEmails() {
        return this.emailRepository.findAll();
    }

}
