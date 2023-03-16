package fr.univ.orleans.miage.servicenotification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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



}
