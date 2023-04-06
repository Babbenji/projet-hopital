package com.example.producer;

import com.example.modele.DTO.EmailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RabbitMQProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    @Value("${spring.rabbitmq.exchange-rdvpatients}")
    private String exchange;
    @Value("${spring.rabbitmq.routingkey-rdvpatients}")
    private String routingKey;
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendEmail(EmailDTO emailDTO){
        LOGGER.info(String.format("Email envoyé -> %s", emailDTO.toString()));
        rabbitTemplate.convertAndSend(exchange,routingKey,emailDTO);
    }
    public void sendProduits(Collection<String> listeProduits){
        rabbitTemplate.convertAndSend(exchange,routingKey,listeProduits);
    }
}
