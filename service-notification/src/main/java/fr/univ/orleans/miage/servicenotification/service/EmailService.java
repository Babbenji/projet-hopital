package fr.univ.orleans.miage.servicenotification.service;

public interface EmailService {

    void sendEmail(String to, String subject, String body);
}
