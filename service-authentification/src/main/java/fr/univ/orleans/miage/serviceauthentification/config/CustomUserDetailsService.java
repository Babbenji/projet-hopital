package fr.univ.orleans.miage.serviceauthentification.config;

import fr.univ.orleans.miage.serviceauthentification.facade.FacadeUtilisateur;
import fr.univ.orleans.miage.serviceauthentification.facade.exceptions.UtilisateurInexistantException;
import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *  Cette classe implémente l'interface UserDetailsService de Spring Security pour permettre l'authentification des utilisateurs.
 *  Elle permet de récupérer les informations d'un utilisateur depuis la base de données en utilisant l'email de l'utilisateur qui souhaite s'authentifier.
 * @version : 1.0
 */
public class CustomUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    private FacadeUtilisateur facadeUtilisateur;

    /**
     * Constructeur de la classe CustomUserDetailsService.
     * @param passwordEncoder L'objet PasswordEncoder utilisé pour encoder les mots de passe des utilisateurs.
     * @param facadeUtilisateur La façade pour accéder aux informations des utilisateurs.
     */
    public CustomUserDetailsService(PasswordEncoder passwordEncoder, FacadeUtilisateur facadeUtilisateur) {
        this.passwordEncoder = passwordEncoder;
        this.facadeUtilisateur = facadeUtilisateur;
    }

    /**
     * Cette méthode est appelée lorsqu'un utilisateur essaie de s'authentifier.
     * Elle récupère les informations de l'utilisateur à partir de son email d'utilisateur.
     * Puis, encode son mot de passe et renvoie un objet UserDetails contenant ces informations.
     * @param s L'email de l'utilisateur à récupérer.
     * @return Un objet UserDetails contenant les informations de l'utilisateur.
     * @throws UsernameNotFoundException Si aucun utilisateur n'est trouvé avec l'identifiant spécifié.
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Utilisateur utilisateur = null;
        try {
            utilisateur = facadeUtilisateur.getUtilisateurByEmail(s);

        } catch (UtilisateurInexistantException e) {
            throw new UsernameNotFoundException("User "+s+" not found");
        }

        UserDetails userdetails = User.builder()
                .username(utilisateur.getEmail())
                .password(passwordEncoder.encode(utilisateur.getPassword()))
                .roles(utilisateur.getRole().name())
                .build();

        return userdetails;
    }

}
