package fr.univ.servicegestionstockfournisseurs.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

@Service
public class RabbitMQProducer {
    @Value("spring.rabbitmq.exchange-msg")
    private String exchange;

    @Value("spring.rabbitmq.routingkey-msg")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);


    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void alerteStock(String message){
        LOGGER.info(String.format("Message sent AARON BLABLA-> %s", message));
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}

