package fr.univ.orleans.miage.servicenotification;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableRabbit
@EnableDiscoveryClient
@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "API Service Notification",
        version = "1.0",
        description = "Documentation OpenAPI du service de notification du Micro-service Hôpital <br> Projet Master 2 MIAGE Université d'Orléans"))

public class ServiceNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceNotificationApplication.class, args);
    }

}
