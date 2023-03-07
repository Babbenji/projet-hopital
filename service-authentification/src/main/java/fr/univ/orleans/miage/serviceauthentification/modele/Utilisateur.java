package fr.univ.orleans.miage.serviceauthentification.modele;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "utilisateur")
public class Utilisateur {//implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;

    @NonNull
    @Column(unique = true)
    private String email;
    @NonNull
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    @NonNull
    private Role role;

    public Utilisateur(String email, String mdp,Role role) {
        this.email = email;
        this.motDePasse = mdp;
        this.role = role;
    }

    public Utilisateur(String nom, String prenom, @NonNull String email, @NonNull String motDePasse, @NonNull Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public Utilisateur() {}

   // @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    //@Override//
    public String getPassword() {
        return motDePasse;
    }

//    @Override
//    public String getUsername() {
//        return email;
//    }

//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
