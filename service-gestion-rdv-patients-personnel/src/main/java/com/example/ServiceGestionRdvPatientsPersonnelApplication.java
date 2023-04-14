package com.example;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableDiscoveryClient
@SpringBootApplication
@EnableMongoRepositories
@OpenAPIDefinition(info = @Info(
		title = "API Service Gestion RDV patients et personnel",
		version = "1.0",
		description = "Documentation OpenAPI du service de gestion des RDV des patients et du personnel",
		license = @License(name = "Université d'Orléans", url = "https://www.univ-orleans.fr/fr")))

public class ServiceGestionRdvPatientsPersonnelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceGestionRdvPatientsPersonnelApplication.class, args);
	}

}
