package fr.univ.orleans.miage.serviceauthentification.service;


import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ConsulService {
    private final ResourceLoader resourceLoader;

    public ConsulService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    public void storePublicKey() throws Exception {
        WebClient webClient = WebClient.create();
        Resource resource = resourceLoader.getResource("classpath:app.pub");
        Path filePath = Paths.get(resource.getURI());
        String url = String.format("http://%s/v1/kv/config/crytographie/clepublique", "localhost:8500");
        WebClient.ResponseSpec responseSpec = webClient.put()
                .uri(url)
                .contentType(MediaType.TEXT_PLAIN)
                .bodyValue(Files.readString(filePath))
                .retrieve();

        HttpStatusCode responseStatus = responseSpec.toBodilessEntity().block().getStatusCode();

        if (responseStatus != HttpStatus.OK) {
            throw new Exception("Failed to store public key in Consul");
        }

    }

}

