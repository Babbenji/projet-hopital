package fr.univ.orleans.miage.serviceauthentification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class UserDTO {

    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[\\p{L} '-]+$")
    private String nom;

    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[\\p{L} '-]+$")
    private String prenom;

    @Email
    @Size(min=8,max=50)
    @NotNull
    String email;

    @Size(min=4,max=128)
    @NotNull
    String password;

}
