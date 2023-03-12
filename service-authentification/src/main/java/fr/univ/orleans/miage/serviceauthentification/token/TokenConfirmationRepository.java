package fr.univ.orleans.miage.serviceauthentification.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenConfirmationRepository extends JpaRepository<TokenConfirmation,Long> {

    Optional<TokenConfirmation> findByToken(String token);
}
