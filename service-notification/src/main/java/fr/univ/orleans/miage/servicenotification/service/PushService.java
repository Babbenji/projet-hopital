package fr.univ.orleans.miage.servicenotification.service;

import fr.univ.orleans.miage.servicenotification.modele.Push;

import java.util.List;

public interface PushService {

    /**
     * Envoie une notification push
     * @param destinataire
     * @param objet
     * @param contenu
     */
    void envoyerNotifPush(String destinataire, String objet, String contenu);


    /**
     * Récupère toutes les notifications push envoyées
     * @return List<Push> liste de toutes les notifications envoyées
     */
    List<Push> getAllNotifPush();
}
