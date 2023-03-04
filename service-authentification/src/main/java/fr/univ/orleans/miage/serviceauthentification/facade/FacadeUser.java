package fr.univ.orleans.miage.serviceauthentification.facade;


import fr.univ.orleans.miage.serviceauthentification.facade.exceptions.UserDejaExistantException;
import fr.univ.orleans.miage.serviceauthentification.facade.exceptions.UserInexistantException;
import fr.univ.orleans.miage.serviceauthentification.modele.User;

import java.util.Collection;

public interface FacadeUser {

    /**
     * Permet de créer un compte utilisateur
     * @param email
     * @param mdp
     * @return User
     * @thorws UserDejaExistantException
     */
    User inscription(String email, String mdp) throws UserDejaExistantException;

    /**
     * Permet de récupérer un utilisateur par son email
     * @param email
     * @return User
     * @throws UserInexistantException
     */
    User getUserByEmail(String email) throws UserInexistantException;

    /**
     * Permet de se supprimer un utilisateur du système
     * @param email
     * @throws UserInexistantException
     */
    void desincription(String email) throws UserInexistantException;

    /**
     * Permet de vérifier si un utilisateur existe
     * @param email
     * @return
     */
    boolean userExists(String email);


    /**
     * Permet de vérifier si un utilisateur est connecté
     * @param email
     * @return
     */
    boolean isUserConnected(String email);

    /**
     * Permet de récupérer la liste des utilisateurs du système
     * @return Collection<User>
     *     Liste des utilisateurs
     *     null si aucun utilisateur n'est trouvé
     *     null si une erreur est survenue
     *     null si le système est indisponible
     */
    Collection<User> getAllUsers();


    /**
     * Permet de récupérer la liste des utilisateurs par rôle
     * @param role
     * @return Collection<User>
     *     Liste des utilisateurs
     *     null si aucun utilisateur n'est trouvé
     *     null si une erreur est survenue
     */
    Collection<User> getUsersByRole(String role);

}
