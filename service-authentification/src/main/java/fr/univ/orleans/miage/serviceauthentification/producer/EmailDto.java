package fr.univ.orleans.miage.serviceauthentification.producer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDto {

    @NotBlank
    @Email
    private String destinataire;

    @NotBlank
    private String objet;
    @NotBlank
    private String contenu;

    @NotBlank
    private String type = "simple";

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
