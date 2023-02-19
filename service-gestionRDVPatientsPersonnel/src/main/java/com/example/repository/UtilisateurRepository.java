package com.example.repository;


import com.example.modele.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer>, MongoRepository<Utilisateur, Integer> {


}
