package com.example.repository;

import com.example.modele.Medecin;
import com.example.modele.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends MongoRepository<Patient, Integer>, CrudRepository<Patient,Integer> {

    Patient findByNumsecu_pat(String numsecu_pat);
    Patient findById_uti(int id_uti);

    void removeById_cons(int id_cons);

    Patient findPatientByEmail_uti(String email_uti);



}
