package com.example.controleur;

import com.example.exceptions.*;
import com.example.modele.*;
import com.example.facade.FacadeApplication;
import com.example.modele.DTO.ConsultationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/rdvpatients",produces = {MediaType.APPLICATION_JSON_VALUE})
public class Controleur {

    @Autowired
    FacadeApplication facadeApplication;

    @PostMapping("/medecin")
    public ResponseEntity<Medecin> ajouterMedecin(@RequestBody Medecin medecin){
        Medecin nouveauMedecin = facadeApplication.ajouterMedecin(medecin.getPrenom(), medecin.getNom(), medecin.getEmail());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idMedecin}").buildAndExpand(medecin.getId()).toUri();
        return ResponseEntity.created(location).body(nouveauMedecin);
    }
    @PostMapping("/patient")
    public ResponseEntity<Patient> ajouterPatient(@RequestBody Patient patient){
        Patient nouveauPatient = facadeApplication.ajouterPatient(
                patient.getPrenom(),
                patient.getNom(),
                patient.getEmail(),
                patient.getNumSecu(),
                patient.getNumTel(),
                String.valueOf(patient.getDateNaissance()),
                patient.getGenre()
        );
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{numSecu}").buildAndExpand(nouveauPatient.getNumSecu()).toUri();
        return ResponseEntity.created(location).body(nouveauPatient);
    }
    @GetMapping("/patient/{numSecu}")
    public ResponseEntity<String> afficherPatient(@PathVariable("numSecu") String numSecu){
        Patient patient = facadeApplication.getPatientByNumSecu(numSecu);
        return ResponseEntity.ok(patient.toString());
    }
    @PatchMapping("/personnel/modif/patient/{numSecu}/antecedents")
    public ResponseEntity<String> modifierAntecedents(@PathVariable("numSecu") String numSecu, @RequestBody Patient patient) {
        try {
            facadeApplication.modifierAntecedentsPatient(numSecu,patient.getAntecedents());
            return ResponseEntity.ok().body("Les antécédents pour le patient n°"+numSecu+" : \n"+patient.getAntecedents());
        } catch (PatientInexistantException e) {
            return ResponseEntity.status(404).body("Utilisateur inexistant");
        }
    }
    @PatchMapping("/personnel/modif/patient/{numSecu}/medecintraitant")
    public ResponseEntity<String> assignerMedecinTraitant(@PathVariable("numSecu") String numSecu, @RequestBody Medecin medecin) {
        facadeApplication.assignerMedecinTraitant(numSecu,medecin.getPrenom(),medecin.getNom());
        return ResponseEntity.ok().body("Le médecin assigné au patient n°"+numSecu+"\n est "+medecin.getPrenom()+" "+medecin.getNom());
    }

    @PostMapping("/consultation")
    public ResponseEntity<Consultation> prendreRDV(@RequestBody ConsultationDTO consultationDTO){
        Patient patient = facadeApplication.getPatientByEmail("brosseau.aaron@gmail.com");
        Consultation nouvelleConsultation = facadeApplication.prendreRDV(patient,consultationDTO.getDateRDV(),consultationDTO.getHeureRDV(),consultationDTO.getMotif(),consultationDTO.getOrdonnance(),consultationDTO.getType());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idConsultation}").buildAndExpand(nouvelleConsultation.getId()).toUri();
        return ResponseEntity.created(location).body(nouvelleConsultation);
    }
    @PatchMapping("/consultation/{idConsultation}/confirmer")
    public ResponseEntity<String> confirmerRDV( @PathVariable("idConsultation") int idConsultation) {
        facadeApplication.confirmerRDV(idConsultation);
        return ResponseEntity.ok().body("La consultation n°"+idConsultation+" a bien été confirmée.");
    }

    @PatchMapping("/consultation/{idConsultation}/compterendu")
    public ResponseEntity<String> modifierCR( @PathVariable("idConsultation") int idConsultation, @RequestBody Consultation consultation) {
        facadeApplication.modifierCRConsultation(idConsultation,consultation.getCompteRendu());
        return ResponseEntity.ok().body("Le compte rendu pour la consultation n°"+idConsultation+" :\n est : "+consultation.getCompteRendu());
    }
    @GetMapping("/medecin/{idMedecin}/consultations")
    public ResponseEntity<Collection<Consultation>> voirConsultationsMedecin(@PathVariable("idMedecin") int idMedecin) {
        List<Consultation> consultations = facadeApplication.voirConsultationsMedecin(idMedecin);
        return ResponseEntity.ok().body(consultations);
    }




    // A modifier
    @PostMapping("/consultation/{idConsultation}/annulation")
    public ResponseEntity<String> annulationRDV(@PathVariable("idConsultation") int idConsultation) {
        facadeApplication.annulerConsultation(idConsultation);
        return ResponseEntity.ok().body("Annulation de la consultation n°"+idConsultation);
    }
}