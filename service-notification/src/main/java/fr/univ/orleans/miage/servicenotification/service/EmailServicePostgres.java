package fr.univ.orleans.miage.servicenotification.service;

import fr.univ.orleans.miage.servicenotification.modele.Email;
import fr.univ.orleans.miage.servicenotification.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmailServicePostgres implements EmailService{

    private final EmailRepository emailRepository;
    private final JavaMailSender javaMailSender;

//    @Autowired
    public EmailServicePostgres(EmailRepository emailRepository, JavaMailSender javaMailSender) {
        this.emailRepository = emailRepository;
        this.javaMailSender = javaMailSender;
    }

    /**
     * Envoie un email à un destinataire et l'enregistre dans la base de données Postgres
     * @param destinataire
     * @param objet
     * @param contenu
     */
    @Override
    public void envoyerEmail(String destinataire, String objet, String contenu) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply.hopital@gmail.com");
        message.setTo(destinataire);
        message.setSubject(objet);
        message.setText(contenu);

        this.javaMailSender.send(message);
        this.emailRepository.save(new Email(destinataire, objet, contenu));
    }

    public List<Email> getAllEmails() {
        return this.emailRepository.findAll();
    }

//    public void addEmail(Email email) {
//        this.emailRepository.save(email);
//    }

    public Long compterEmails() {
        return this.emailRepository.count();
    }

    public Email getEmailById(Long id) {
        return this.emailRepository.findById(id).orElse(null);
    }

//    public Email updateEmail(Email email) {
//        return this.emailRepository.save(email);
//    }

//    public String deleteEmail(Long id) {
//        this.emailRepository.deleteById(id);
//        return "Email supprimé avec succès!";
//    }

//    public Email getEmailByDestinataire(String destinataire) {
//        return this.emailRepository.findByDestinataire(destinataire);
//    }
//
//    public String deleteEmailByDestinataire(String destinataire) {
//        this.emailRepository.deleteByEmailDestinataire(destinataire);
//        return "Email supprimé avec succès!";
//    }

//    public String deleteAllEmails() {
//        this.emailRepository.deleteAll();
//        return "Tous les emails ont été supprimés avec succès!";
//    }
//
//    public String deleteAllEmailsByDestinataire(String destinataire) {
//        this.emailRepository.deleteAllByEmailDestinataire(destinataire);
//        return "Tous les emails ont été supprimés avec succès!";
//    }
//
//    //récupérer tous les emails d'une periode donnée
//    public List<Email> getAllEmailsByDate(String dateDebut, String dateFin) {
//        return this.emailRepository.findAllByEmailDateBetween(dateDebut, dateFin);
//    }

    //récupérer tous les emails d'un destinataire pour une periode donnée
//    public List<Email> getAllEmailsByDateAndDestinataire(String dateDebut, String dateFin, String destinataire) {
//        return this.emailRepository.findAllByEmailDateBetweenAndDestinataire(dateDebut, dateFin, destinataire);
//    }


}
