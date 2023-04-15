package fr.univ.servicegestionstockfournisseurs;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.bson.Document;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Date;

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
