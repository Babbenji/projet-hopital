package fr.univ.orleans.miage.serviceauthentification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @Email @Size(min=8,max=50)
    String email;

    @Size(min=4,max=128)
    String password;

    public UserDTO() {
    }

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
