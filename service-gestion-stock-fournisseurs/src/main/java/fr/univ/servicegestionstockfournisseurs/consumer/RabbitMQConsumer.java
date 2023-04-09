package fr.univ.servicegestionstockfournisseurs.consumer;

import fr.univ.servicegestionstockfournisseurs.modele.DTO.FactureDTO;
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
     * consomme la facture et modifie la quantité des produits
     * @param factureDTO
     * @throws ProduitInexistantException
     */
    @RabbitListener(queues = {"${spring.rabbitmq.queue}"})
    public FactureDTO consume(FactureDTO factureDTO) throws ProduitInexistantException
    {
        for (Map.Entry<String, Integer> entry : factureDTO.getListeProduits().entrySet())
        {
            LOGGER.info(String.format("La quantite du produit " + entry.getKey() + " a été modifiée de " + entry.getValue()));
            //facadeServiceGestionStock.modifierQuantiteProduitMedical(entry.getKey(),entry.getValue());
        }

        return factureDTO;
    }

}
