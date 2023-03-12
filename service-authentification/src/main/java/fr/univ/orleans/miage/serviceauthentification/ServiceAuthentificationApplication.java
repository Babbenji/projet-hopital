package fr.univ.orleans.miage.serviceauthentification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class ServiceAuthentificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthentificationApplication.class, args);
    }


}
