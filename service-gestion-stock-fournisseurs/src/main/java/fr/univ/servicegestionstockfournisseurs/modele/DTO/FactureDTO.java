package fr.univ.servicegestionstockfournisseurs.modele.DTO;

import java.util.List;
import java.util.Map;

public class FactureDTO {

    private PatientDTO patient;

    private String type;

    private Map<String,Integer> listeProduits;

    private double coutDuPatient;


    public double getCoutDuPatient() {
        return coutDuPatient;
    }

    public void setCoutDuPatient(double coutDuPatient) {
        this.coutDuPatient = coutDuPatient;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
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
                "patient=" + patient +
                ", type='" + type + '\'' +
                ", listeProduits=" + listeProduits +
                '}';
    }
}
