package fr.univ.servicegestionstockfournisseurs.repository;

import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import fr.univ.servicegestionstockfournisseurs.modele.Fournisseur;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FournisseurRepository extends MongoRepository<Fournisseur, Integer>, CrudRepository<Fournisseur,Integer> {

    boolean existsByIdFournisseur(int idFournisseur);
    boolean existsByNomFournisseur(String nomFournisseur);
    boolean existsByCatalogueFournisseur(int idProduit);
    Fournisseur findByIdFournisseur(int idFournisseur);
    void deleteByIdFournisseur(int idFournisseur);
}
