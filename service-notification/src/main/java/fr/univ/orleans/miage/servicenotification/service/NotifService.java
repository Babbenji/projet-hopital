package fr.univ.orleans.miage.servicenotification.service;

import fr.univ.orleans.miage.servicenotification.modele.Notif;

import java.util.List;

public interface NotifService {

    /**
     * Envoie une notification push
     * @param destinataire
     * @param objet
     * @param contenu
     */
    void envoyerNotifPush(String destinataire, String objet, String contenu);


    /**
     * Récupère toutes les notifications push envoyées
     * @return List<Notif> liste de toutes les notifications envoyées
     */
    List<Notif> getAllNotifPush();
}
