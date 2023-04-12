package com.example.producer;

import com.example.modele.DTO.EmailDTO;
import com.example.modele.DTO.FactureDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RabbitMQProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    @Value("${spring.rabbitmq.exchange-email}")
    private String exchangeNotif;
    @Value("${spring.rabbitmq.routingkey-email}")
    private String routingkeyNotif;

    @Value("${spring.rabbitmq.exchange-stock}")
    private String exchangeStock;
    @Value("${spring.rabbitmq.routingkey-stock}")
    private String routingkeyStock;
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendEmail(EmailDTO emailDTO){
        LOGGER.info(String.format("Email envoyé -> %s", emailDTO.toString()));
        rabbitTemplate.convertAndSend(exchangeNotif,routingkeyNotif,emailDTO);
    }
    public void sendFacture(FactureDTO factureDTO){
        LOGGER.info(String.format("Facture envoyée -> %s", factureDTO));
        rabbitTemplate.convertAndSend(exchangeStock,routingkeyStock,factureDTO);
    }
}
