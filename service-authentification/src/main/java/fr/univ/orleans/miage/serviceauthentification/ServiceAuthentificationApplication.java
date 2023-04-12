package fr.univ.orleans.miage.serviceauthentification;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableRabbit
@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(
        title = "API Service Authentification",
        version = "1.0",
        description = "Documentation OpenAPI du service d'authentification du micro-service Hôpital <br> Projet Master 2 MIAGE Université d'Orléans",
        license = @License(name = "Université d'Orléans", url = "https://www.univ-orleans.fr/fr")))

public class ServiceAuthentificationApplication {

    public static void main(String[] args) {SpringApplication.run(ServiceAuthentificationApplication.class, args);}


}
