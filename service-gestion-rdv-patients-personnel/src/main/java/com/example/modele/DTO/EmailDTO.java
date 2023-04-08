package com.example.modele.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {

    @NotBlank
    @Email
    private String destinataire;
    @NotBlank
    private String objet;
    @NotBlank
    private String contenu;

    @NotBlank
    private String type = "simple";

    public String getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EmailDto{" +
                "destinataire='" + destinataire + '\'' +
                ", objet='" + objet + '\'' +
                ", contenu='" + contenu + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
