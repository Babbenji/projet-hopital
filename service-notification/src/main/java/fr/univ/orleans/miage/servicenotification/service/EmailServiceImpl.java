package fr.univ.orleans.miage.servicenotification.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void envoyerEmail(String destinataire, String objet, String contenu) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply.hopital@gmail.com");
        message.setTo(destinataire);
        message.setSubject(objet);
        message.setText(contenu);

        this.javaMailSender.send(message);
    }
}
