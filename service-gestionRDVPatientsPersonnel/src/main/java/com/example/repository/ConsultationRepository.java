package com.example.repository;

import com.example.modele.Consultation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends MongoRepository<Consultation, Integer>, CrudRepository<Consultation,Integer> {
    Consultation findConsultationByIdCons(int idCons);
    void removeByIdCons(int idCons);

}
