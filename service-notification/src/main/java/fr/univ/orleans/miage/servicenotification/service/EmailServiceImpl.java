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

    @Override
    public void envoyerEmail(Email email) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply.hopital@gmail.com");
        message.setTo(email.getDestinataire());
        message.setSubject(email.getObjet());
        message.setText(email.getContenu());

        this.javaMailSender.send(message);
        this.emailRepository.save(new Email(email.getDestinataire(), email.getObjet(), email.getContenu()));

    }

    public List<Email> getAllEmails() {
        return this.emailRepository.findAll();
    }



}
