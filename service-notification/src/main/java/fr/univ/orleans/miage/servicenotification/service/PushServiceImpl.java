package fr.univ.orleans.miage.servicenotification.service;

import fr.univ.orleans.miage.servicenotification.modele.Push;
import fr.univ.orleans.miage.servicenotification.repository.PushRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushServiceImpl implements PushService {

    @Autowired
    private PushRepository pushRepository;

    @Override
    public void envoyerNotifPush(String destinataire, String objet, String contenu) {
        this.pushRepository.save(new Push(destinataire, objet, contenu));
    }

    @Override
    public List<Push> getAllNotifPush() {
        return this.pushRepository.findAll();
    }
}
