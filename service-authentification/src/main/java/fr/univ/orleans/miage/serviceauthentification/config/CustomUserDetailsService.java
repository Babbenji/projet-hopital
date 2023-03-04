package fr.univ.orleans.miage.serviceauthentification.config;

import fr.univ.orleans.miage.serviceauthentification.facade.FacadeUser;
import fr.univ.orleans.miage.serviceauthentification.facade.exceptions.UserInexistantException;
import fr.univ.orleans.miage.serviceauthentification.modele.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomUserDetailsService implements UserDetailsService {

    private static final String[] ROLES_ADMIN = {"USER","ADMIN"};
    private static final String[] NO_ROLE={};

    private PasswordEncoder passwordEncoder;
    private FacadeUser facadeUser;

    public CustomUserDetailsService(PasswordEncoder passwordEncoder, FacadeUser facadeUser) {
        this.passwordEncoder = passwordEncoder;
        this.facadeUser = facadeUser;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User utilisateur = null;
        try {
            utilisateur = facadeUser.getUserByEmail(s);
        } catch (UserInexistantException e) {
            throw new UsernameNotFoundException("User "+s+" not found");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(utilisateur.getEmail())
                .password(passwordEncoder.encode(utilisateur.getMotDePasse()))
                .roles(NO_ROLE)
                .build();
    }
}
