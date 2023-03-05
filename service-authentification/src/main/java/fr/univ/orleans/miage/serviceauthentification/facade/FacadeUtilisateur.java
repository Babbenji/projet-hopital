package fr.univ.orleans.miage.serviceauthentification.facade;


import fr.univ.orleans.miage.serviceauthentification.facade.exceptions.UtilisateurDejaExistantException;
import fr.univ.orleans.miage.serviceauthentification.facade.exceptions.UtilisateurInexistantException;
import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;

import java.util.Collection;
import java.util.Optional;

public interface FacadeUtilisateur {

    /**
     * Permet de créer un compte utilisateur
     * @param email
     * @param mdp
     * @return Utilisateur
     * @thorws UtilisateurDejaExistantException
     */
    Utilisateur inscription(String email, String mdp) throws UtilisateurDejaExistantException;

    /**
     * Permet de récupérer un utilisateur par son email
     *
     * @param email
     * @return Utilisateur
     * @throws UtilisateurInexistantException
     */
    Utilisateur getUtilisateurByEmail(String email) throws UtilisateurInexistantException;

    /**
     * Permet de se supprimer un utilisateur du système
     * @param email
     * @throws UtilisateurInexistantException
     */
    void desincription(String email) throws UtilisateurInexistantException;

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
    boolean isUtilisateurConnected(String email);

    /**
     * Permet de récupérer la liste des utilisateurs du système
     * @return Collection<Utilisateur>
     *     Liste des utilisateurs
     *     null si aucun utilisateur n'est trouvé
     *     null si une erreur est survenue
     *     null si le système est indisponible
     */
    Collection<Utilisateur> getAllUtilisateurs();


    /**
     * Permet de récupérer la liste des utilisateurs par rôle
     * @param role
     * @return Collection<Utilisateur>
     *     Liste des utilisateurs
     *     null si aucun utilisateur n'est trouvé
     *     null si une erreur est survenue
     */
    Collection<Utilisateur> getUtilisateursByRole(String role);

}
