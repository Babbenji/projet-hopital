package fr.univ.orleans.miage.serviceauthentification.repository;

import fr.univ.orleans.miage.serviceauthentification.modele.Role;
import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Utilisateur findByEmail(String email);
    
    Boolean existsByEmail(String email);

    Collection<Utilisateur> findByRole(@NonNull Role role);
}
