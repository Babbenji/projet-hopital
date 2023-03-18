package com.example.repository;

import com.example.modele.Consultation;
import com.example.modele.Medecin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedecinRepository extends MongoRepository<Medecin, Integer>, CrudRepository<Medecin,Integer> {
    Medecin findMedecinByPrenomAndNom(String prenom, String nom);
    Medecin findMedecinById(int id);
    List<Consultation> getById(int id);
    void removeById(int id);


    boolean existsByEmail(String email);
}
