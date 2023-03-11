package fr.univ.orleans.miage.servicenotification.service;

import fr.univ.orleans.miage.servicenotification.modele.Email;

import java.util.List;

public interface EmailService {

    /**
     * Envoie un email à un destinataire
     * @param destinataire
     * @param objet
     * @param contenu
     */
    void envoyerEmail(String destinataire, String objet, String contenu);

    /**
     * Récupère tous les emails envoyés
     * @return List<Email> liste de tous les emails envoyés
     */
    List<Email> getAllEmails();
}
