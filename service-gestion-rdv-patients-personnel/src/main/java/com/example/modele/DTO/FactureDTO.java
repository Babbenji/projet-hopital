package com.example.modele.DTO;

import com.example.modele.Patient;

import java.util.Map;

public class FactureDTO {
    private Patient patient;
    private String type;
    private Map<String,Integer> listeProduits;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Integer> getListeProduits() {
        return listeProduits;
    }

    public void setListeProduits(Map<String, Integer> listeProduits) {
        this.listeProduits = listeProduits;
    }

    @Override
    public String toString() {
        return "FactureDTO{" +
                "Patient=" + patient.toString() +
                ", type='" + type + '\'' +
                ", listeProduits=" + listeProduits +
                '}';
    }
}
