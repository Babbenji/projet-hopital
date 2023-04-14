package fr.univ.orleans.miage.serviceauthentification.token;

import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TokenConfirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String token;
    @NotNull
    private LocalDateTime dateCreation;
    @NotNull
    private LocalDateTime dateExpiration;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "utilisateur_id"
    )
    private Utilisateur utilisateur;


    public TokenConfirmation(String token,
                             LocalDateTime dateCreation,
                             LocalDateTime dateExpiration,
                             Utilisateur utilisateur) {
        this.token = token;
        this.dateCreation = dateCreation;
        this.dateExpiration = dateExpiration;
        this.utilisateur = utilisateur;
    }
}
