package fr.univ.orleans.miage.servicenotification.modele;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "emails_envoyes")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @jakarta.validation.constraints.Email
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


    public Email() {
    }


    public Email(Long id, String destinataire, String objet, String contenu) {
        this.id = id;
        this.destinataire = destinataire;
        this.objet = objet;
        this.contenu = contenu;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(String to) {
        this.destinataire = to;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String subject) {
        this.objet = subject;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String body) {
        this.contenu = body;
    }
}
