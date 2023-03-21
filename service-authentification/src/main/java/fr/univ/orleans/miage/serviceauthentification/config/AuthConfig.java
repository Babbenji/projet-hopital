package fr.univ.orleans.miage.serviceauthentification.config;

import fr.univ.orleans.miage.serviceauthentification.service.UtilisateurService;
import fr.univ.orleans.miage.serviceauthentification.modele.Utilisateur;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Map;
import java.util.function.Function;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Cette classe configure l'authentification avec Spring Security et OAuth2 en utilisant JWT et RSA pour la signature des tokens.
 * Elle contient les configurations pour la sécurité des endpoints, la gestion des sessions, la génération de token JWT et la validation de token JWT.
 * @version : 1.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AuthConfig {

    @Value("${jwt.public.key}")
    RSAPublicKey key;

    @Value("${jwt.private.key}")
    RSAPrivateKey priv;

    /**
     * Configuration de la sécurité des endpoints HTTP.
     * Elle permet de configurer les autorisations d'accès aux endpoints, la protection CSRF,
     * la gestion des sessions et la validation des tokens JWT OAuth2.
     * @param http l'objet HttpSecurity de Spring Security.
     * @return le filtre de sécurité.
     * @throws Exception si une erreur survient pendant la configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/api/producer/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v*/auth/inscription/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v*/auth/connexion").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v*/auth/confirmation-compte").permitAll()
                        //les autres endpoints nécessitent une authentification et une autorisation qui sont gérées dans les contrôleurs par les annotations @PreAuthorize
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler()));

        return http.build();
    }

    /**
     * Implémentation du service de gestion des utilisateurs.
     * @param utilisateurService le service de gestion des utilisateurs.
     * @param passwordEncoder l'encodeur de mot de passe.
     * @return le service de gestion des utilisateurs.
     */
    @Bean
    UserDetailsService users(UtilisateurService utilisateurService, PasswordEncoder passwordEncoder) {
        return new CustomUserDetailsService(passwordEncoder, utilisateurService);
    }

    /**
     * Configuration du décodeur de token JWT.
     * @return le décodeur de token JWT configuré avec la clé publique.
     */
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    /**
     * Configuration de l'encodeur de token JWT.
     * @return l'encodeur de token JWT configuré avec la clé privée.
     */
    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    /**
     * Configuration de l'encodeur de mot de passe. Chiffrement par défaut : BCrypt.
     * @return l'encodeur de mot de passe.
     */
    @Bean
    public PasswordEncoder delegatingPasswordEncoder() {

        // Définit l'encodeur par défaut.
        String idForEncode = "bcrypt";;
        PasswordEncoder defaultEncoder = new BCryptPasswordEncoder();

        // Liste des autres encodeurs disponibles.
        Map<String, PasswordEncoder> encoders = Map.of(
                idForEncode, defaultEncoder,
                "noop", NoOpPasswordEncoder.getInstance(),
                "scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v4_1(),
                "sha256", new StandardPasswordEncoder()
        );
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

/*    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
*/

    /**
     * Fonction qui génère un token JWT à partir de l'utilisateur fourni.
     * @param jwtEncoder l'encodeur de token JWT.
     * @return une fonction qui prend un utilisateur en entrée et renvoie un token JWT.
     */
    @Bean
    Function<Utilisateur,String> genereTokenFunction(JwtEncoder jwtEncoder) {
        return user -> {
            // Récupère l'instant courant et la durée de validité du token.
            Instant now = Instant.now();
            long expiry = 36000L;

            // Crée un objet JwtClaimsSet contenant les informations de l'utilisateur.
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiry))
                    .subject(user.getEmail())
//                    .claim("scope", user.getRole().name())
                    .claim("scope", user.getRole())
                    .build();

            // Encode le token JWT et renvoie sa valeur.
            return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        };
    }




}