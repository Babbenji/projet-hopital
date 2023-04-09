package fr.univ.servicegestionstockfournisseurs.producer;

import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

@Service
public class RabbitMQProducer {
    @Value("spring.rabbitmq.exchange-facture")
    private String exchange;

    @Value("spring.rabbitmq.routingkey-facture")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);


    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void envoieCommande(Commande commande)
    {
        LOGGER.info("Commande envoyée pour facture {}", commande);
        rabbitTemplate.convertAndSend("facture.exchange", "factureCmd.routingKey", commande);
    }

//    public void envoieNotificationStockBas(EmailDTO emailDTO)
//    {
//        LOGGER.info(String.format("Notification envoyée pour stock bas", emailDTO));
//        rabbitTemplate.convertAndSend("stock.exchange", "stock.routingKey", emailDTO);
//    }
}

