package fr.univ.orleans.miage.servicenotification.service;

import fr.univ.orleans.miage.servicenotification.modele.Email;
import jakarta.mail.MessagingException;

import java.util.List;

public interface EmailService {

    /**
     * Envoie un email à un destinataire et l'enregistre dans la base de données
     * @param email à envoyer
     */
    void envoyerEmail(Email email);

    /**
     * Envoie un email à un destinataire et l'enregistre dans la base de données
     * @param emailHtml à envoyer
     */
    void envoyerEmailHtml(Email emailHtml) throws MessagingException;

    /**
     * Récupère tous les emails envoyés
     * @return liste de tous les emails envoyés
     */
    List<Email> getAllEmails();


}
