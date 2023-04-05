package com.example.producer;

import com.example.modele.DTO.EmailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange-rdvpatients}")
    private String exchange;
    @Value("${rabbitmq.routingkey-rdvpatients}")
    private String routingKey;
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendEmail(EmailDTO emailDTO){
        rabbitTemplate.convertAndSend(exchange,routingKey,emailDTO);
    }
}
