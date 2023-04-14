package fr.univ.servicegestionstockfournisseurs.repository;

import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends MongoRepository<Commande, Integer>, CrudRepository<Commande,Integer> {
    Commande findByIdCommande(int idCommande);
    boolean existsByIdCommande(int idCommande);
    void deleteByIdCommande(int idCommande);



}
