package com.example.repository;

import com.example.modele.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends MongoRepository<Patient, Integer>, CrudRepository<Patient,Integer> {


    Patient findPatientByNumSecu(String numSecu);
    Patient findPatientByEmail(String email);
    Patient findPatientById(int id);
    void removePatientById(int id);
}
