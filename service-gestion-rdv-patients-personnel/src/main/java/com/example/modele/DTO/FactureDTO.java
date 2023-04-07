package com.example.modele.DTO;

import java.util.List;

public class FactureDTO {
    private int idPatient;
    private String type;
    private List<String> listeProduits;

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

    public List<String> getListeProduits() {
        return listeProduits;
    }

    public void setListeProduits(List<String> listeProduits) {
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
