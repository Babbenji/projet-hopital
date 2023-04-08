package com.example.modele.DTO;

import java.util.List;
import java.util.Map;

public class FactureDTO {
    private int idPatient;
    private String type;
    private Map<String,Integer> listeProduits;

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
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
                "idPatient=" + idPatient +
                ", type='" + type + '\'' +
                ", listeProduits=" + listeProduits +
                '}';
    }
}
