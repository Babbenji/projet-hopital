package com.example.modele.DTO;

public class ConsultationDTO {
    private String dateRDV;
    private String heureRDV;
    private String motif;
    private String type;

    public String getDateRDV() {
        return dateRDV;
    }

    public void setDateRDV(String dateRDV) {
        this.dateRDV = dateRDV;
    }

    public String getHeureRDV() {
        return heureRDV;
    }

    public void setHeureRDV(String heureRDV) {
        this.heureRDV = heureRDV;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}