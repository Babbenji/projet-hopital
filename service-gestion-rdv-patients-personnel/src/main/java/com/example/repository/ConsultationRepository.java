package com.example.repository;

import com.example.modele.Consultation;
import com.example.modele.TypeCons;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ConsultationRepository extends MongoRepository<Consultation, Integer>, CrudRepository<Consultation,Integer> {
    Consultation findConsultationById(int id);
    void removeConsultationById(int id);
    Collection<Consultation> findAllConsultationsByType(TypeCons type);
}
