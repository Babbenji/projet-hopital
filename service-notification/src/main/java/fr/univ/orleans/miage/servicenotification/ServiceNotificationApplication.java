package fr.univ.orleans.miage.servicenotification;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
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
        description = "Documentation OpenAPI du service de notification du micro-service Hôpital " +
                      "<br> Projet Innovation SI, Master 2 MIAGE, Université d'Orléans, 2022-2023",
        license =@License(name = "Université d'Orléans", url = "https://www.univ-orleans.fr/fr")))

public class ServiceNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceNotificationApplication.class, args);
    }

}
