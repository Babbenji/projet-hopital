package fr.univ.servicegestionstockfournisseurs.repository;


import fr.univ.servicegestionstockfournisseurs.modele.Commande;
import fr.univ.servicegestionstockfournisseurs.modele.ProduitMedical;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitMedicalRepository extends MongoRepository<ProduitMedical, Integer>, CrudRepository<ProduitMedical,Integer> {

    ProduitMedical findByIdProduitMedical(int idProduitMedical);

    ProduitMedical findByNomProduitMedical(String nomProduit);
    boolean existsByNomProduitMedical(String nomProduit);
    boolean existsByIdProduitMedical(int idProduitMedical);
    void deleteByIdProduitMedical(int idProduitMedical);
    ProduitMedical findProduitMedicalByNomProduitMedical(String nomProduit);

}
