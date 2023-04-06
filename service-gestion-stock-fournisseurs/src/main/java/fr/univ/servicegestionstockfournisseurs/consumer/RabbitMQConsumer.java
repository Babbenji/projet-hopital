package fr.univ.servicegestionstockfournisseurs.consumer;

import fr.univ.servicegestionstockfournisseurs.service.FacadeServiceGestionStock;
import fr.univ.servicegestionstockfournisseurs.service.exceptions.ProduitInexistantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RabbitMQConsumer {

    @Autowired
    FacadeServiceGestionStock facadeServiceGestionStock;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    /**
     * Recois un objet de type Map<String,Integer> contenant la liste des médicaments utilisés pour un patients et permettera de mettre à jour le stock
     * @param listeMedicaments
     */
    @RabbitListener(queues = {"${spring.rabbitmq.queue}"})
    public void consume(List<String> listeMedicaments) throws ProduitInexistantException {
        Map<String, Integer> map = new HashMap<>();
        for (String couple:listeMedicaments) {
            String[] keyValue = couple.split(":");
            String key = keyValue[0];
            Integer value = Integer.valueOf(keyValue[1]);
            map.put(key, value);
        }

        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            facadeServiceGestionStock.modifierQuantiteProduitMedical(entry.getKey(),entry.getValue());
            LOGGER.info(String.format("La quantite du produit" + entry.getKey() + "a été modifiée de " + entry.getValue()));
        }
    }
}
