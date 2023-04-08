package com.example.producer;

import com.example.modele.DTO.EmailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class RabbitMQProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    @Value("${spring.rabbitmq.exchange-rdvpatients}")
    private String exchange;
    @Value("${spring.rabbitmq.routingkey-rdvpatients}")
    private String routingKey;

    @Value("${spring.rabbitmq.exchange-stock}")
    private String exchangeStock;
    @Value("${spring.rabbitmq.routingkey-stock}")
    private String routingKeyStock;
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendEmail(EmailDTO emailDTO){
        LOGGER.info(String.format("Email envoyé -> %s", emailDTO.toString()));
        rabbitTemplate.convertAndSend(exchange,routingKey,emailDTO);
    }
    public void sendProduits(List<String> listeProduits){
        for (String prod : listeProduits){
            LOGGER.info("-------------------- Produit -> {}", prod);
        }
        rabbitTemplate.convertAndSend(exchangeStock,routingKeyStock,listeProduits);
    }
}
