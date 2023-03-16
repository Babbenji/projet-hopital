package fr.univ.orleans.miage.serviceauthentification.facade;

import fr.univ.orleans.miage.serviceauthentification.facade.exceptions.*;
import fr.univ.orleans.miage.serviceauthentification.modele.Role;
import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;
import fr.univ.orleans.miage.serviceauthentification.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("facadeUtilisateur")
public class FacadeUtilisateurImpl implements FacadeUtilisateur {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public Utilisateur inscription(String email, String mdp) throws UtilisateurDejaExistantException {
        if(utilisateurRepository.existsByEmail(email))
            throw new UtilisateurDejaExistantException();

        // Détermination du rôle de l'utilisateur en fonction du domaine de l'email
        String[] res = email.split("@");
        Role role;

        if (res[1].equals("hopital-medecin.fr") && !res[0].equals("admin"))
        {
            role = Role.MEDECIN;
        }
        else if(res[1].equals("hopital-secretaire.fr"))
        {
            role = Role.SECRETAIRE;
        }
        else if(res[1].equals("hopital-comptable.fr"))
        {
            role = Role.COMPTABLE;
        }
        else {
            role = Role.PATIENT;
        }

        Utilisateur u = new Utilisateur(email, mdp,role);
        this.utilisateurRepository.save(u);
        return u;
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
        for (Role r : Role.values()) {
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
        Collection <Utilisateur> utilisateurs = utilisateurRepository.findByRole(Role.valueOf(role));

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
