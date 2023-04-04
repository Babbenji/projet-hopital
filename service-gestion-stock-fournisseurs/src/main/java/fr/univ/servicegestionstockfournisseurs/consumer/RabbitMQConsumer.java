package fr.univ.servicegestionstockfournisseurs.consumer;

import fr.univ.servicegestionstockfournisseurs.service.FacadeServiceGestionStock;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.ProduitInexistantException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Map;

@Service
public class RabbitMQConsumer {

    FacadeServiceGestionStock facadeServiceGestionStock;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    /**
     * Recois un objet de type Map<String,Integer> contenant la liste des médicaments utilisés pour un patients et permettera de mettre à jour le stock
     * @param listeMedicaments
     */
    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(Map<String,Integer> listeMedicaments) throws ProduitInexistantException {
        for (Map.Entry<String, Integer> entry : listeMedicaments.entrySet())
        {
            facadeServiceGestionStock.modifierQuantiteProduitMedical(entry.getKey(),entry.getValue());
            LOGGER.info(String.format("La quantite du produit" + entry.getKey() + "a été modifiée de " + entry.getValue()));
        }
    }
}
