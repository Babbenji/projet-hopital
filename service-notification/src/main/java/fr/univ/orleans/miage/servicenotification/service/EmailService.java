package fr.univ.orleans.miage.servicenotification.service;

public interface EmailService {

    void envoyerEmail(String destinataire, String objet, String contenu);
}
