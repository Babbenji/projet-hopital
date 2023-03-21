package fr.univ.servicegestionstockfournisseurs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Commande extends MongoRepository<Commande, Integer> {
}
