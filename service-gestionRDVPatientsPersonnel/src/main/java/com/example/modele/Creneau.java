package com.example.modele;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "creneau")

public class Creneau {
    private Date dateCren;
    private String heureCren;
    private boolean dispoCren;

    public Creneau() {}
    public Creneau(Date dateCren, String heureCren) {
        this.dateCren = dateCren;
        this.heureCren = heureCren;
        this.dispoCren =true;
    }

    public Date getDateCren() {
        return dateCren;
    }
    public void setDateCren(Date dateCren) {
        this.dateCren = dateCren;
    }
    public String getHeureCren() {
        return heureCren;
    }
    public void setHeureCren(String heureCren) {
        this.heureCren = heureCren;
    }
    public boolean estDispo() {
        return dispoCren;
    }
    public void setDispoCren(boolean dispoCren) {
        this.dispoCren = dispoCren;
    }
}
