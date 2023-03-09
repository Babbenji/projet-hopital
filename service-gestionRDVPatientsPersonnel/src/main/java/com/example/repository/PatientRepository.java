package com.example.repository;

import com.example.modele.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends MongoRepository<Patient, Integer>, CrudRepository<Patient,Integer> {

    Patient findByNumSecuPat(String numSecuPat);
    Patient findByIdUti(int idUti);

    void removeByIdUti(int idUti);

    Patient findPatientByEmailUti(String emailUti);



}
