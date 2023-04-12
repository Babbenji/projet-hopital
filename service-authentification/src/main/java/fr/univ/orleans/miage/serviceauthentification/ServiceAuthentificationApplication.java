package fr.univ.orleans.miage.serviceauthentification;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
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
        description = "Documentation OpenAPI du service d'authentification du Micro-service Hôpital <br> Projet Master 2 MIAGE Université d'Orléans"))
public class ServiceAuthentificationApplication {

    public static void main(String[] args) {SpringApplication.run(ServiceAuthentificationApplication.class, args);}


}
