package fr.univ.servicegestionstockfournisseurs.repository;

import fr.univ.servicegestionstockfournisseurs.modele.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends MongoRepository<Utilisateur, Integer>, CrudRepository<Utilisateur, Integer>
{
    Utilisateur findUtilisateurByIdUtilisateur(int idUtilisateur);
    boolean existsUtilisateurByIdUtilisateur(int idUtilisateur);
    boolean existsByEmailUtilisateur(String emailUtilisateur);
}
