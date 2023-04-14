package fr.univ.orleans.miage.serviceauthentification.repository;

import fr.univ.orleans.miage.serviceauthentification.modele.ERole;
import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Utilisateur findByEmail(String email);
    
    Boolean existsByEmail(String email);

    Collection<Utilisateur> findByRole(@NonNull ERole role);

    @Transactional
    @Modifying
    @Query("UPDATE Utilisateur u " +
            "SET u.compteActive = TRUE WHERE u.email = ?1")
    int activerCompteUtilisateur(String email);
}
