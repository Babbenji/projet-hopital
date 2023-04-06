package com.example.modele;

import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.*;

@Document(collection = "consultation")
public class Consultation {
    private static int IDS = 1;
    private int id;
    private Creneau creneau;
    private TypeCons type;
    private String motif;
    private String compteRendu;
    private String ordonnance;
    private boolean confirmation;
    private int idMedecin;
    private int idPatient;
    private String dateCreation;
    private String dateModification;
    private List<String> listeProduitsMedicaux;
    public Consultation(Creneau creneau, String motif, TypeCons type, String ordonnance, int idMedecin, int idPatient) {
        this.id=IDS++;
        this.creneau = creneau;
        this.type = type;
        this.motif = motif;
        this.compteRendu =null;
        this.ordonnance = ordonnance;
        this.confirmation = false;
        this.idMedecin = idMedecin;
        this.idPatient = idPatient;
        this.dateCreation = LocalDate.now().toString();
        this.dateModification = LocalDate.now().toString();
        this.listeProduitsMedicaux =new ArrayList<>();
    }
    public int getId() {
        return id;
    }
    public Creneau getCreneau() {
        return creneau;
    }
    public void setCreneau(Creneau creneau) {
        this.creneau = creneau;
    }
    public String getMotif() {
        return motif;
    }
    public void setMotif(String motif) {
        this.motif = motif;
    }
    public String getCompteRendu() {
        return compteRendu;
    }
    public void setCompteRendu(String compteRendu) {
        this.compteRendu = compteRendu;
    }
    public String getOrdonnance() {
        return ordonnance;
    }
    public void setOrdonnance(String ordonnance) {
        this.ordonnance = ordonnance;
    }
    public int getIdMedecin() {
        return idMedecin;
    }
    public void setIdMedecin(int idMedecin) {
        this.idMedecin = idMedecin;
    }
    public boolean estConfirme() {
        return confirmation;
    }
    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }
    public int getIdPatient() {
        return idPatient;
    }
    public void setPatient(int idPatient) {
        this.idPatient = idPatient;
    }
    public TypeCons getType() {
        return type;
    }
    public void setType(TypeCons type) {
        this.type = type;
    }
    public String getDateCreation() {
        return dateCreation;
    }
    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }
    public String getDateModification() {
        return dateModification;
    }
    public void setDateModification(String dateModification) {
        this.dateModification = dateModification;
    }

    public List<String> getListeProduitsMedicaux() {
        return listeProduitsMedicaux;
    }

    public void setListeProduitsMedicaux(List<String> listeProduitsMedicaux) {
        this.listeProduitsMedicaux = listeProduitsMedicaux;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "id=" + id +
                ", creneau=" + creneau +
                ", type=" + type +
                ", motif='" + motif + '\'' +
                ", compteRendu='" + compteRendu + '\'' +
                ", ordonnance='" + ordonnance + '\'' +
                ", confirmation=" + confirmation +
                ", idMedecin=" + idMedecin +
                ", idPatient=" + idPatient +
                ", dateCreation='" + dateCreation + '\'' +
                ", dateModification='" + dateModification + '\'' +
                ", listeProduitsMedicaux=" + listeProduitsMedicaux +
                '}';
    }
}
