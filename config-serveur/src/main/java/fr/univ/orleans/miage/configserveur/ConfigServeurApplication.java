package fr.univ.orleans.miage.configserveur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class ConfigServeurApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServeurApplication.class, args);
    }

}
