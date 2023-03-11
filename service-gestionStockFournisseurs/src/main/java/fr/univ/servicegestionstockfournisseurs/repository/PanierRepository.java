package fr.univ.servicegestionstockfournisseurs.repository;

import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import fr.univ.servicegestionstockfournisseurs.modele.Panier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanierRepository extends MongoRepository<Panier, Integer>, CrudRepository<Panier,Integer> {
    Panier findByIdPanier(int idPanier);
    boolean existsByIdPanier(int idPanier);
}
