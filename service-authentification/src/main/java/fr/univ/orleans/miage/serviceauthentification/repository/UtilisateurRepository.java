package fr.univ.orleans.miage.serviceauthentification.repository;

import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Utilisateur findByEmail(String email);
    
    Boolean existsByEmail(String email);

    void deleteByEmail(String email);

    Collection<Utilisateur> findByRole(String role);
}
