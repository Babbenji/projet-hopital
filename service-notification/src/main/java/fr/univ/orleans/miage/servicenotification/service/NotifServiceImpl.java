package fr.univ.orleans.miage.servicenotification.service;

import fr.univ.orleans.miage.servicenotification.modele.Notif;
import fr.univ.orleans.miage.servicenotification.repository.NotifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifServiceImpl implements NotifService {

    @Autowired
    private NotifRepository notifRepository;

    @Override
    public void envoyerNotifPush(String destinataire, String objet, String contenu) {
        this.notifRepository.save(new Notif(destinataire, objet, contenu));
    }

    @Override
    public List<Notif> getAllNotifPush() {
        return this.notifRepository.findAll();
    }
}
