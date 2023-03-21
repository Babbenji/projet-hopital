package fr.univ.orleans.miage.serviceauthentification.service;

import fr.univ.orleans.miage.serviceauthentification.producer.EmailDto;
import fr.univ.orleans.miage.serviceauthentification.producer.RabbitMqSender;
import fr.univ.orleans.miage.serviceauthentification.service.exceptions.*;
import fr.univ.orleans.miage.serviceauthentification.modele.ERole;
import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;
import fr.univ.orleans.miage.serviceauthentification.repository.UtilisateurRepository;
import fr.univ.orleans.miage.serviceauthentification.token.TokenConfirmation;
import fr.univ.orleans.miage.serviceauthentification.token.TokenConfirmationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Service("utilisateurService")
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final TokenConfirmationService tokenConfirmationService;
    private final RabbitMqSender rabbitMqSender;

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurServiceImpl.class);

    @Override
    public String inscriptionConfirmation(String email, String mdp) throws UtilisateurDejaExistantException {

        if(utilisateurRepository.existsByEmail(email)) {
            throw new UtilisateurDejaExistantException();
        }
        ERole role = determinerRole(email);
        Utilisateur user = new Utilisateur(email,mdp,role,true);
        this.utilisateurRepository.save(user);

        // Création du token de confirmation de compte

        String token = UUID.randomUUID().toString();
        TokenConfirmation tokenConfirmation = new TokenConfirmation(token, LocalDateTime.now(),
                                                LocalDateTime.now().plusMinutes(5), user);
        this.tokenConfirmationService.saveTokenConfirmation(tokenConfirmation);

        // Création du mail de confirmation et délégation de l'envoi au service de Notif par le biais de RabbitMQ

        String lienValidation = "http://localhost:8081/api/v1/auth/confirmation-compte?token=" + token;


        String contenu = "<html><body>";
        contenu += "<p>Bonjour,</p>";
        contenu += "<p>Merci de cliquer sur le bouton ci-dessous pour valider votre compte :</p>";
        contenu += "<a href=\"" + lienValidation + "\"><button style=\"background-color: #008CBA; border: none; color: white; padding: 15px 32px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin: 4px 2px; cursor: pointer;\">Valider mon compte</button></a>";
        contenu += "<p>Attention, ce lien sera valide pendant 5 minutes.</p>";
        contenu += "</body></html>";

//        EmailDto emailDto = new EmailDto();
//        emailDto.setDestinataire(user.getEmail());
//        emailDto.setObjet("Confirmation de compte");
//        emailDto.setContenu(contenu);
//        emailDto.setType("html");

        EmailDto emailDto = EmailDto.builder()
                .destinataire(user.getEmail())
                .objet("Confirmation de compte")
                .contenu(contenu)
                .type("html")
                .build();
//        emailDto.setContenu("Bonjour, merci de cliquer sur le lien suivant pour valider votre compte : " + lienValidation);

        this.rabbitMqSender.send(emailDto);

        logger.info("Event Producer Email send to rabbitmq : " + emailDto);

        return token;
    }

    @Override
    public void confirmationCompte(String tokenConfirmation) throws TokenExpirationException, CompteDejaActiveException {

        TokenConfirmation token = tokenConfirmationService.getTokenConfirmation(tokenConfirmation);

        if(LocalDateTime.now().isAfter(token.getDateExpiration())) {
            throw new TokenExpirationException("Le token a expiré");
        }
        Utilisateur utilisateur = token.getUtilisateur();
        if (utilisateur.isEnabled()) {
            throw new CompteDejaActiveException("Le compte est déjà activé");
        }
        utilisateur.setCompteActive(true);
        utilisateurRepository.save(utilisateur);
//        utilisateurRepository.activerCompteUtilisateur(utilisateur.getEmail());
    }

    @Override
    public boolean verifierActivationCompte(String email) throws CompteDejaActiveException{

        Utilisateur user = utilisateurRepository.findByEmail(email);

        boolean isActif = user.isEnabled();
        if (!isActif) {
            throw new CompteDejaActiveException("Le compte est déjà activé");
        }
        return isActif;
    }

    @Override
    public int activerCompteUser(String email) {
        return utilisateurRepository.activerCompteUtilisateur(email);
    }

    @Override
    public Utilisateur inscription(String email, String mdp) throws UtilisateurDejaExistantException {

        if(utilisateurRepository.existsByEmail(email)) {
            throw new UtilisateurDejaExistantException();
        }
        ERole role = determinerRole(email);
        Utilisateur user = new Utilisateur(email,mdp,role, false);
        this.utilisateurRepository.save(user);

        return user;
    }

    /** Permet de déterminer le rôle de l'utilisateur en fonction du domaine de l'email
     * @param email l'email de l'utilisateur
     * @return le rôle de l'utilisateur associé à l'email
     */
    private ERole determinerRole(String email) {
        String[] res = email.split("@");

        if (res[1].equals("hopital.fr") && res[0].equals("admin")) {
            return ERole.ADMIN;
        } else if (res[1].equals("hopital-medecin.fr") && !res[0].equals("admin")) {
            return ERole.MEDECIN;
        } else if (res[1].equals("hopital-secretaire.fr")) {
            return ERole.SECRETAIRE;
        } else if (res[1].equals("hopital-comptable.fr")) {
            return ERole.COMPTABLE;
        } else {
            return ERole.PATIENT;
        }
    }

    @Override
    public Utilisateur getUtilisateurByEmail(String email) throws UtilisateurInexistantException {
        if(!utilisateurRepository.existsByEmail(email))
            throw new UtilisateurInexistantException();
        return this.utilisateurRepository.findByEmail(email);
    }

    @Override
    public void desincription(String email) throws UtilisateurInexistantException {
        if(!utilisateurRepository.existsByEmail(email))
            throw new UtilisateurInexistantException();
        Utilisateur compteAsupprimer = this.utilisateurRepository.findByEmail(email);
        this.utilisateurRepository.delete(compteAsupprimer);
    }

    @Override
    public boolean userExists(String email) {
        return utilisateurRepository.existsByEmail(email);
    }

    @Override
    public Collection<Utilisateur> getAllUtilisateurs() throws DonneesIntrouvablesException {

        Collection<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        if (utilisateurs.isEmpty()) {
            throw new DonneesIntrouvablesException("Aucun utilisateur trouvé");
        }
        return utilisateurs;
    }

    @Override
    public boolean verifierRole(String role) {
        for (ERole r : ERole.values()) {
            if (r.name().equalsIgnoreCase(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<Utilisateur> getUtilisateursByRole(String role) throws InformationsFourniesIncorrectesException,
                                                                DonneesIntrouvablesException, RoleInvalideException {

        if ( role.isEmpty() || role.isBlank()) {
            throw new InformationsFourniesIncorrectesException("Le role est vide ou null");
        }
        if (!verifierRole(role)) {
            throw new RoleInvalideException("Le rôle fourni est invalide : " + role);
        }
        Collection <Utilisateur> utilisateurs = utilisateurRepository.findByRole(ERole.valueOf(role));

        if (utilisateurs == null || utilisateurs.isEmpty()) {
            throw new DonneesIntrouvablesException("Aucun utilisateur trouvé pour le rôle : " + role);
        } else {
            return utilisateurs;
        }
    }

    @Override
    public void modifierMotDePasse(String email, String nouveauPassword) throws UtilisateurInexistantException {
        if(!utilisateurRepository.existsByEmail(email))
            throw new UtilisateurInexistantException();
        Utilisateur user = this.utilisateurRepository.findByEmail(email);
        user.setMotDePasse(nouveauPassword);
        this.utilisateurRepository.save(user);
    }


}
