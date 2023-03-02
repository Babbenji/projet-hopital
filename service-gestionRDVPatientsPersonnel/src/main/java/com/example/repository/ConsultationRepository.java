package com.example.repository;

import com.example.modele.Consultation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends MongoRepository<Consultation, Integer>, CrudRepository<Consultation,Integer> {
    Consultation findConsultationById_cons(int id_cons);

    void removeById_cons(int id_cons);

}
