package com.example.repository;

import com.example.modele.Creneau;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreneauRepository extends MongoRepository<Creneau, Integer>, CrudRepository<Creneau,Integer> {
}
