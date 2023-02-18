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
    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply.hopital@gmail.com");
//        message.setFrom("");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        this.javaMailSender.send(message);
    }
}
