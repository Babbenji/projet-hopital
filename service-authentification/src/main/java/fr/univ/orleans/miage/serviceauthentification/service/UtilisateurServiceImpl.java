package fr.univ.orleans.miage.serviceauthentification.service;

import fr.univ.orleans.miage.serviceauthentification.service.exceptions.*;
import fr.univ.orleans.miage.serviceauthentification.modele.ERole;
import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;
import fr.univ.orleans.miage.serviceauthentification.repository.UtilisateurRepository;
import fr.univ.orleans.miage.serviceauthentification.token.TokenConfirmation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Service("utilisateurService")
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public Utilisateur inscription(String email, String mdp) throws UtilisateurDejaExistantException {
        if(utilisateurRepository.existsByEmail(email))
            throw new UtilisateurDejaExistantException();


        // Détermination du rôle de l'utilisateur en fonction du domaine de l'email
        String[] res = email.split("@");

        ERole role;

        if (res[1].equals("hopital-medecin.fr") && !res[0].equals("admin"))
        {
            role = ERole.MEDECIN;
        }
        else if(res[1].equals("hopital-secretaire.fr"))
        {
            role = ERole.SECRETAIRE;
        }
        else if(res[1].equals("hopital-comptable.fr"))
        {
            role = ERole.COMPTABLE;
        }
        else {
            role = ERole.PATIENT;
        }


//        Role role;
//        if (res[1].equals("hopital-medecin.fr") && !res[0].equals("admin")) {
//            role = new Role(ERole.MEDECIN,"Compte médecin");
//        }
//        else if (res[1].equals("hopital-secretaire.fr")) {
//            role = new Role(ERole.SECRETAIRE,"Compte secrétaire médicale");
//        }
//        else if (res[1].equals("hopital-comptable.fr")) {
//            role = new Role(ERole.COMPTABLE,"Compte comptable pour le service facturation");
//        }
//        else {
//            role = new Role(ERole.PATIENT,"Compte utilisateur simple");
//        }


//        // Détermination du rôle de l'utilisateur en fonction du domaine de l'email
//        String[] res = email.split("@");
//        Role role = new Role();
//
//        if (res[1].equals("hopital-medecin.fr") && !res[0].equals("admin"))
//        {
//            role.setERole(ERole.MEDECIN);
//        }
//        else if(res[1].equals("hopital-secretaire.fr"))
//        {
//            role.setERole(ERole.SECRETAIRE);
//        }
//        else if(res[1].equals("hopital-comptable.fr"))
//        {
//            role.setERole(ERole.COMPTABLE);
//        }
//        else {
//            role.setERole(ERole.PATIENT);
//        }

        Utilisateur user = new Utilisateur(email,mdp,role);
        this.utilisateurRepository.save(user);
//        return user;

        //TOKEN DE CONFIRMATION

        String token = UUID.randomUUID().toString();
        //TODO Envoie du token de confirmation
        TokenConfirmation tokenConfirmation = new TokenConfirmation(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5),
                user
        );

        return user;

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
        if (utilisateurs == null || utilisateurs.isEmpty()) {
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
    public Collection<Utilisateur> getUtilisateursByRole(String role) throws InformationsFourniesIncorrectesException, DonneesIntrouvablesException, RoleInvalideException {

        if ( role.isEmpty() || role.isBlank() || role == null) {
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
