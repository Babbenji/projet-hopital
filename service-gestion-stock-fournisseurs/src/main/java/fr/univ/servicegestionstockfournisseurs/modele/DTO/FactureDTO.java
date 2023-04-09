package fr.univ.servicegestionstockfournisseurs.modele.DTO;

import java.util.List;
import java.util.Map;

public class FactureDTO {

    private PatientDTO patient;

    private String type;

    private Map<String,Integer> listeProduits;


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
}
