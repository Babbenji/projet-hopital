package fr.univ.orleans.miage.serviceauthentification;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableRabbit
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceAuthentificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthentificationApplication.class, args);
    }

    /**
     * Initialisation des utilisateurs
     */
//    @Bean
//    public InitUtilisateursRunner initUsersRunner() {
//        return new InitUtilisateursRunner();
//    }




}
