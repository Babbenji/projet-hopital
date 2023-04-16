package fr.univ.orleans.miage.serviceauthentification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class VaultService {

    @Autowired
    private VaultTemplate vaultTemplate;

    @Value("classpath:app.pub")
    private Resource publicKeyResource;

    @Value("classpath:app.key")
    private Resource privateKeyResource;


    public void storeKeyPair() {

        try {
            InputStream publicKeyInputStream = publicKeyResource.getInputStream();
            String publicKeyContent = new String(publicKeyInputStream.readAllBytes(), StandardCharsets.UTF_8);

            InputStream privateKeyInputStream = privateKeyResource.getInputStream();
            String privateKeyContent = new String(privateKeyInputStream.readAllBytes(), StandardCharsets.UTF_8);

            Map<String, String> keyPair = new HashMap<>();
            keyPair.put("privateKey", privateKeyContent);
            keyPair.put("publicKey", publicKeyContent);

            vaultTemplate.opsForVersionedKeyValue("secret").put("application", keyPair);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}