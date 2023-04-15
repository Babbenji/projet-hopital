package fr.univ.servicegestionstockfournisseurs.producer;

import fr.univ.servicegestionstockfournisseurs.consumer.RabbitMQConsumer;
import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import fr.univ.servicegestionstockfournisseurs.modele.DTO.EmailDTO;
import fr.univ.servicegestionstockfournisseurs.modele.DTO.FactureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

@Service
public class RabbitMQProducer {
    @Value("${spring.rabbitmq.exchange-facture}")
    private String exchangeFacture;

    @Value("${spring.rabbitmq.routingkey-facture-fournisseur}")
    private String routingKeyFactureFournisseur;

    @Value("${spring.rabbitmq.routingkey-facture-patient}")
    private String routingKeyFacturePatient;

    @Value("${spring.rabbitmq.exchange-notification-stock-bas}")
    private String exchangeNotificationStockBas;

    @Value("${spring.rabbitmq.routingkey-notification-stock-bas}")
    private String routingKeyNotificationStockBas;



    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);


    private final RabbitTemplate rabbitTemplate;


    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void envoieCommande(Commande commande)
    {
        LOGGER.info("Commande envoyée pour facture {}", commande);
        rabbitTemplate.convertAndSend(exchangeFacture, routingKeyFactureFournisseur, commande);
    }

    public void envoieFacturePatient(FactureDTO factureDTO)
    {
        LOGGER.info("Facture envoyée pour patient {}", factureDTO);
        rabbitTemplate.convertAndSend(exchangeFacture, routingKeyFacturePatient, factureDTO);
    }

    public void envoieNotificationStockBas(String nomProduit)
    {
        LOGGER.info("Notification envoyée pour stock bas", nomProduit);
        rabbitTemplate.convertAndSend(exchangeNotificationStockBas, routingKeyNotificationStockBas, nomProduit);
    }
}

