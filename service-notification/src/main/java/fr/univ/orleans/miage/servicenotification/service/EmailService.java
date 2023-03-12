package fr.univ.orleans.miage.servicenotification.service;

import fr.univ.orleans.miage.servicenotification.modele.Email;

import java.util.List;

public interface EmailService {

    /**
     * Envoie un email à un destinataire et l'enregistre dans la base de données
     * @param email à envoyer
     */
    void envoyerEmail(Email email);

    /**
     * Récupère tous les emails envoyés
     * @return liste de tous les emails envoyés
     */
    List<Email> getAllEmails();


}
