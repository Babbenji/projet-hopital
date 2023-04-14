package fr.univ.orleans.miage.servicenotification.modele;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifs_push")
public class Notif {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "token")
    private String token;

    @Column(name = "titre")
    private String titre;
    @Column(name = "message", columnDefinition = "text")
    private String message;

    @Column(name = "date_envoi")
    private LocalDateTime dateEnvoi;

    @PrePersist
    protected void onCreate() {
        dateEnvoi = LocalDateTime.now();
    }

    public Notif(String token, String titre, String message) {
        this.token = token;
        this.titre = titre;
        this.message = message;
    }

    public Notif() {
    }

    public Notif(Long id, String token, String titre, String message) {
        this.id = id;
        this.token = token;
        this.titre = titre;
        this.message = message;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String destinataire) {
        this.token = destinataire;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String objet) {
        this.titre = objet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String contenu) {
        this.message = contenu;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }



    @Override
    public String toString() {
        return "Notif { " +
                "id = " + id +
                ", destinataire = '" + token + '\'' +
                ", objet = '" + titre + '\'' +
                ", contenu = '" + message + '\'' +
                ", dateEnvoi =  " + dateEnvoi +
                '}';
    }
}
