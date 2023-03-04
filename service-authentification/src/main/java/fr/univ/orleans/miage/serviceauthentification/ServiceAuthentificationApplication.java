package fr.univ.orleans.miage.serviceauthentification;

import fr.univ.orleans.miage.serviceauthentification.facade.FacadeUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class ServiceAuthentificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthentificationApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(FacadeUser facadeUser, PasswordEncoder passwordEncoder) {
        return args -> facadeUser.inscription("yohan.boichut@gmail.com", passwordEncoder.encode("pass"));
    }

}
