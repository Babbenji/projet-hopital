package com.example.modele;

import java.time.LocalDate;
import java.util.Date;

public class Consultation {
    private int id_cons;
    private Creneau creneau_cons;
    private String motif_cons;
    private String cr_cons;
    private String ordo_cons;
    private Medecin medecin_cons;
    private boolean confirm_cons;
    private Patient patient_cons;
    private TypeCons type_cons;
    private LocalDate datecrea_cons;
    private LocalDate datemodif_cons;
    private LocalDate dateannul_cons;

    public Consultation(Creneau creneau_cons, String motif_cons, String ordo_cons, Medecin medecin_cons, Patient patient_cons, TypeCons type_cons) {
        this.creneau_cons = creneau_cons;
        this.motif_cons = motif_cons;
        this.ordo_cons = ordo_cons;
        this.medecin_cons = medecin_cons;
        this.patient_cons = patient_cons;
        this.type_cons = type_cons;
        this.datecrea_cons = LocalDate.now();
        this.datemodif_cons = LocalDate.now();
    }

    public int getId_cons() {
        return id_cons;
    }

    public void setId_cons(int id_cons) {
        this.id_cons = id_cons;
    }

    public Creneau getCreneau_cons() {
        return creneau_cons;
    }

    public void setCreneau_cons(Creneau creneau_cons) {
        this.creneau_cons = creneau_cons;
    }

    public String getMotif_cons() {
        return motif_cons;
    }

    public void setMotif_cons(String motif_cons) {
        this.motif_cons = motif_cons;
    }

    public String getCr_cons() {
        return cr_cons;
    }

    public void setCr_cons(String cr_cons) {
        this.cr_cons = cr_cons;
    }

    public String getOrdo_cons() {
        return ordo_cons;
    }

    public void setOrdo_cons(String ordo_cons) {
        this.ordo_cons = ordo_cons;
    }

    public Medecin getMedecin_cons() {
        return medecin_cons;
    }

    public void setMedecin_cons(Medecin medecin_cons) {
        this.medecin_cons = medecin_cons;
    }

    public boolean isConfirm_cons() {
        return confirm_cons;
    }

    public void setConfirm_cons(boolean confirm_cons) {
        this.confirm_cons = confirm_cons;
    }

    public Patient getPatient_cons() {
        return patient_cons;
    }

    public void setPatient_cons(Patient patient_cons) {
        this.patient_cons = patient_cons;
    }

    public TypeCons getType_cons() {
        return type_cons;
    }

    public void setType_cons(TypeCons type_cons) {
        this.type_cons = type_cons;
    }

    public LocalDate getDatecrea_cons() {
        return datecrea_cons;
    }

    public void setDatecrea_cons(LocalDate datecrea_cons) {
        this.datecrea_cons = datecrea_cons;
    }

    public LocalDate getDatemodif_cons() {
        return datemodif_cons;
    }

    public void setDatemodif_cons(LocalDate datemodif_cons) {
        this.datemodif_cons = datemodif_cons;
    }

    public LocalDate getDateannul_cons() {
        return dateannul_cons;
    }

    public void setDateannul_cons(LocalDate dateannul_cons) {
        this.dateannul_cons = dateannul_cons;
    }

    public Consultation() {
    }
}
