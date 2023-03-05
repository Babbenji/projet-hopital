package fr.univ.orleans.miage.serviceauthentification.config;

import fr.univ.orleans.miage.serviceauthentification.facade.FacadeUtilisateur;
import fr.univ.orleans.miage.serviceauthentification.facade.exceptions.UtilisateurInexistantException;
import fr.univ.orleans.miage.serviceauthentification.modele.Role;
import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;
    private FacadeUtilisateur facadeUser;


    public CustomUserDetailsService(PasswordEncoder passwordEncoder, FacadeUtilisateur facadeUser) {
        this.passwordEncoder = passwordEncoder;
        this.facadeUser = facadeUser;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Utilisateur utilisateur = null;
        try {
            utilisateur = facadeUser.getUtilisateurByEmail(s);

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
