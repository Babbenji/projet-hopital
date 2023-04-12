package fr.univ.servicegestionstockfournisseurs;

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
        title = "API Service Gestion Stock Fournisseurs",
        version = "1.0",
            description = "Documentation OpenAPI du service de gestion des stocks et des fournisseurs",
        license = @License(name = "Université d'Orléans", url = "https://www.univ-orleans.fr/fr")))

public class ServiceGestionStockFournisseursApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceGestionStockFournisseursApplication.class, args);
    }

}
