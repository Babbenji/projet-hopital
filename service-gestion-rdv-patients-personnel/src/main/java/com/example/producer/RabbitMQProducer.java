package com.example.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    public String exchange = "exchange";
    public String routingKey = "routingKey";
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendMessage(String message){
        LOGGER.info(String.format("Message envoyé : %s", message));
        rabbitTemplate.convertAndSend(exchange,routingKey,message);
    }
}
