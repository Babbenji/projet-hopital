package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableDiscoveryClient
@SpringBootApplication
@EnableMongoRepositories
public class ServiceGestionRdvPatientsPersonnelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceGestionRdvPatientsPersonnelApplication.class, args);
	}

}
