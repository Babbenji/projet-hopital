package fr.univ.orleans.miage.serviceauthentification.controleur;

import fr.univ.orleans.miage.serviceauthentification.producer.EmailDto;
import fr.univ.orleans.miage.serviceauthentification.producer.RabbitMqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/producer/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProducerControleur {

    private final RabbitMqSender rabbitMqSender;

    @Autowired
    public ProducerControleur(RabbitMqSender rabbitMqSender) {
        this.rabbitMqSender = rabbitMqSender;
    }


    @PostMapping(value = "/email")
    public EmailDto publishUserDetails(@RequestBody EmailDto emailDto) {
        rabbitMqSender.send(emailDto);
        return emailDto;
    }
}
