package com.example.modele;

import org.springframework.cglib.core.Local;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Document(collection = "consultation")
public class Consultation {
    private static int IDS = 1;
    private int idCons;
    private Creneau creneauCons;
    private TypeCons typeCons;
    private String motifCons;
    private String compteRenduCons;
    private String ordoCons;
    private boolean confirmCons;
    private Medecin medecinCons;
    private Patient patientCons;
    private Date dateCreaCons;
    private Date dateModifCons;
    private Date dateAnnulCons;

    public Consultation() {}
    public Consultation(Creneau creneauCons, String motifCons, TypeCons typeCons, String ordoCons, Medecin medecinCons, Patient patientCons) {
        this.idCons=IDS++;
        this.creneauCons = creneauCons;
        this.motifCons = motifCons;
        this.typeCons = typeCons;
        this.ordoCons = ordoCons;
        this.medecinCons = medecinCons;
        this.patientCons = patientCons;
        this.dateCreaCons = Date.from(Instant.now());
        this.dateModifCons = Date.from(Instant.now());
    }

    public int getIdCons() {
        return idCons;
    }
    public void setIdCons(int idCons) {
        this.idCons = idCons;
    }
    public Creneau getCreneauCons() {
        return creneauCons;
    }
    public void setCreneauCons(Creneau creneauCons) {
        this.creneauCons = creneauCons;
    }
    public String getMotifCons() {
        return motifCons;
    }
    public void setMotifCons(String motifCons) {
        this.motifCons = motifCons;
    }
    public String getCompteRenduCons() {
        return compteRenduCons;
    }
    public void setCompteRenduCons(String compteRenduCons) {
        this.compteRenduCons = compteRenduCons;
    }
    public String getOrdoCons() {
        return ordoCons;
    }
    public void setOrdoCons(String ordoCons) {
        this.ordoCons = ordoCons;
    }
    public Medecin getMedecinCons() {
        return medecinCons;
    }
    public void setMedecinCons(Medecin medecinCons) {
        this.medecinCons = medecinCons;
    }
    public boolean estConfirme() {
        return confirmCons;
    }
    public void setConfirmCons(boolean confirmCons) {
        this.confirmCons = confirmCons;
    }
    public Patient getPatientCons() {
        return patientCons;
    }
    public void setPatientCons(Patient patientCons) {
        this.patientCons = patientCons;
    }
    public TypeCons getTypeCons() {
        return typeCons;
    }
    public void setTypeCons(TypeCons typeCons) {
        this.typeCons = typeCons;
    }
    public Date getDateCreaCons() {
        return dateCreaCons;
    }
    public void setDateCreaCons(Date dateCreaCons) {
        this.dateCreaCons = dateCreaCons;
    }
    public Date getDateModifCons() {
        return dateModifCons;
    }
    public void setDateModifCons(Date dateModifCons) {
        this.dateModifCons = dateModifCons;
    }
    public Date getDateAnnulCons() {
        return dateAnnulCons;
    }
    public void setDateAnnulCons(Date dateAnnulCons) {
        this.dateAnnulCons = dateAnnulCons;
    }
}
