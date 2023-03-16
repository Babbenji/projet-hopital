package fr.univ.orleans.miage.servicenotification.modele;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "emails_envoyes")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "destinataire")
    private String destinataire;

    @Column(name = "objet")
    private String objet;
    @Column(name = "contenu", columnDefinition = "text")
    private String contenu;

    @Column(name = "date_envoi")
    private LocalDateTime dateEnvoi;

    public Email(String destinataire, String objet, String contenu) {
        this.destinataire = destinataire;
        this.objet = objet;
        this.contenu = contenu;
    }

    @PrePersist
    protected void onCreate() {
        dateEnvoi = LocalDateTime.now();
    }


}
