package fr.univ.orleans.miage.serviceauthentification.facade;

import fr.univ.orleans.miage.serviceauthentification.facade.exceptions.UtilisateurDejaExistantException;
import fr.univ.orleans.miage.serviceauthentification.facade.exceptions.UtilisateurInexistantException;
import fr.univ.orleans.miage.serviceauthentification.modele.Role;
import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;
import fr.univ.orleans.miage.serviceauthentification.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service("facadeUtilisateur")
public class FacadeUtilisateurImpl implements FacadeUtilisateur {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public Utilisateur inscription(String email, String mdp) throws UtilisateurDejaExistantException {
        if(utilisateurRepository.existsByEmail(email))
            throw new UtilisateurDejaExistantException();


        String[] res = email.split("@");
        System.out.println(res.toString());
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
        this.utilisateurRepository.deleteByEmail(email);
    }

    @Override
    public boolean userExists(String email) {
        return utilisateurRepository.existsByEmail(email);
    }

    @Override
    public boolean isUtilisateurConnected(String email) {
        return false;
    }

    @Override
    public Collection<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Collection<Utilisateur> getUtilisateursByRole(String role) {
        return utilisateurRepository.findByRole(role);
    }
}
