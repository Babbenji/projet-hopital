package fr.univ.orleans.miage.serviceauthentification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ServiceAuthentificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthentificationApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner init(FacadeUser facadeUser, PasswordEncoder passwordEncoder) {
//        return args -> facadeUser.inscription("yohan.boichut@gmail.com", passwordEncoder.encode("pass"));
//    }

}
