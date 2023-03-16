package fr.univ.orleans.miage.serviceauthentification.token;

import org.springframework.stereotype.Service;

@Service
public class TokenConfirmationService {

    private final TokenConfirmationRepository tokenConfirmationRepository;

    public TokenConfirmationService(TokenConfirmationRepository tokenConfirmationRepository) {
        this.tokenConfirmationRepository = tokenConfirmationRepository;
    }

    public void saveTokenConfirmation(TokenConfirmation token) {
        tokenConfirmationRepository.save(token);
    }

    public TokenConfirmation getTokenConfirmation(String token) {
        return tokenConfirmationRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Le token n'a pas été trouvé"));
    }
}
