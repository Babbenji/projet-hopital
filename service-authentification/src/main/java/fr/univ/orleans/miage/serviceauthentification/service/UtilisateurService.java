package fr.univ.orleans.miage.serviceauthentification.service;


import fr.univ.orleans.miage.serviceauthentification.service.exceptions.*;
import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;

import java.util.Collection;

public interface UtilisateurService {

    /**
     * Permet de créer un compte utilisateur avec le rôle associé déterminé par le domaine de l'email
     * AVEC ETAPE DE CONFIRMATION DE COMPTE
     * @param email
     * @param mdp
     * @return Utilisateur
     * @thorws UtilisateurDejaExistantException
     */
    String inscriptionConfirmation(String email, String mdp) throws UtilisateurDejaExistantException;

    /** Permet de déterminer le rôle de l'utilisateur en fonction du domaine de l'email
     * @param tokenConfirmation le token de confirmation reçu par mail
     * @return le rôle de l'utilisateur associé à l'email
     */
    void confirmationCompte(String tokenConfirmation) throws TokenExpirationException, CompteDejaActiveException;

    /**
     * Permet de créer un compte utilisateur avec le rôle associé déterminé par le domaine de l'email
     * SANS ETAPE DE CONFIRMATION DE COMPTE
     * @param email
     * @param mdp
     * @return Utilisateur
     * @thorws UtilisateurDejaExistantException
     */
    Utilisateur inscriptionSansConfirmation(String email, String mdp) throws UtilisateurDejaExistantException;

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
     * Permet de récupérer la liste des utilisateurs du système
     * @return Collection<Utilisateur> Liste des utilisateurs
     */
    Collection<Utilisateur> getAllUtilisateurs() throws DonneesIntrouvablesException;

    /**
     * Vérifie si le rôle fourni correspond à l'un des rôles définis dans l'énumération de rôles
     * @param role Rôle à vérifier
     * @return true si le rôle est valide, false sinon
     */
     boolean verifierRole(String role);

    /**
     * Permet de récupérer la liste des utilisateurs par rôle
     * @param role Rôle de l'utilisateur
     * @return Collection<Utilisateur> Liste des utilisateurs
     * @throws InformationsFourniesIncorrectesException si erreur de saisie
     * @throws DonneesIntrouvablesException si aucun utilisateur n'est trouvé
     * @throws RoleInvalideException si rôle invalide
     */
    Collection<Utilisateur> getUtilisateursByRole(String role) throws InformationsFourniesIncorrectesException, DonneesIntrouvablesException, RoleInvalideException;


    /**
     * Permet de modifier le mot de passe d'un utilisateur
     * @param email email de l'utilisateur
     * @param nouveauPassword le nouveau mot de passe encodé
     */
    void modifierMotDePasse(String email, String nouveauPassword) throws UtilisateurInexistantException;


    boolean verifierActivationCompte(String email) throws CompteDejaActiveException;

    int activerCompteUser(String email) throws CompteDejaActiveException;
}
