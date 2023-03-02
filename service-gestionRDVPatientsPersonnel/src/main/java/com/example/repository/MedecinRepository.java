package com.example.repository;

import com.example.modele.Consultation;
import com.example.modele.Medecin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedecinRepository extends MongoRepository<Medecin, Integer>, CrudRepository<Medecin,Integer> {
    Medecin findByPrenom_utiAndNom_uti(String prenom_uti, String nom_uti);
    Medecin findById_uti(int id_uti);

    List<Consultation> getById_uti(int id_uti);
    void removeById_cons(int id_cons);



}
