package fr.univ.orleans.miage.serviceauthentification.facade;

import fr.univ.orleans.miage.serviceauthentification.facade.exceptions.UserDejaExistantException;
import fr.univ.orleans.miage.serviceauthentification.facade.exceptions.UserInexistantException;
import fr.univ.orleans.miage.serviceauthentification.modele.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service("facadeUser")
public class FacadeUserImpl implements FacadeUser{

    /**
     * Dictionnaire des comptes utilisateurs
     */
    private Map<String, User> users;

    public FacadeUserImpl() {this.users = new HashMap<>();}

    @Override
    public User inscription(String email, String mdp) throws UserDejaExistantException {
        if(users.containsKey(email))
            throw new UserDejaExistantException();
        User u = new User(email, mdp);
        this.users.put(email, u);
        return u;
    }

    @Override
    public User getUserByEmail(String email) throws UserInexistantException {
        if(!users.containsKey(email))
            throw new UserInexistantException();
        return users.get(email);
    }

    @Override
    public void desincription(String email) throws UserInexistantException {
        if(!users.containsKey(email))
            throw new UserInexistantException();
        users.remove(email);

    }

    @Override
    public boolean userExists(String email) {
        return false;
    }

    @Override
    public boolean isUserConnected(String email) {
        return false;
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public Collection<User> getUsersByRole(String role) {
        return null;
    }
}
