package com.example.controleur;

import com.example.exceptions.*;
import com.example.modele.*;
import com.example.facade.FacadeApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/api/rdvpatients",produces = {MediaType.APPLICATION_JSON_VALUE})
public class Controleur {
    @Autowired
    FacadeApplication facadeApplication;

    @PostMapping("/medecin/nouveau")
    public ResponseEntity<Medecin> ajouterMedecin(@RequestBody String prenom, @RequestBody String nom, @RequestBody String email) throws AdresseMailDejaUtiliseeException {
        Medecin medecin = facadeApplication.ajouterMedecin(prenom, nom, email);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idMedecin}").buildAndExpand(medecin.getId_uti()).toUri();
        return ResponseEntity.created(location).body(medecin);
    }

    @PostMapping("/patient/nouveau")
    public ResponseEntity<Patient> ajouterPatient(@RequestBody String prenom, @RequestBody String nom, @RequestBody String email, @RequestBody String numsecu, @RequestBody String numtel, @RequestBody String datenais, @RequestBody String genre) throws AdresseMailDejaUtiliseeException {
        Patient nouveauPatient = facadeApplication.ajouterPatient(prenom, nom, email, numsecu, numtel, datenais, genre);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{numSecuPatient}").buildAndExpand(nouveauPatient.getNumsecu_pat()).toUri();
        return ResponseEntity.created(location).body(nouveauPatient);
    }

    @PatchMapping("/personnel/modif/patient/{numSecuPatient}/antecedents")
    public ResponseEntity<String> modifierAntecedents(@PathVariable("numSecuPatient") String numSecuPatient, @RequestBody String antecedents) {
        facadeApplication.modifierAntecedentsPatient(numSecuPatient,antecedents);
        return ResponseEntity.ok().body("Les antécédents pour le patient n°"+numSecuPatient+" : \n"+antecedents);
    }

    @PatchMapping("/personnel/modif/patient/{numSecuPatient}/medecintraitant")
    public ResponseEntity<String> assignerMedecinTraitant( @PathVariable("numSecuPatient") String numSecuPatient, @RequestBody String prenomMedecin, @RequestBody String nomMedecin) {
        facadeApplication.assignerMedecinTraitant(numSecuPatient,nomMedecin,prenomMedecin);
        return ResponseEntity.ok().body("Le médecin assigné au patient n°"+numSecuPatient+"\n est "+prenomMedecin+" "+nomMedecin);
    }

    @PatchMapping("/consultation/{idConsultation}/confirmer")
    public ResponseEntity<String> confirmerRDV( @PathVariable("idConsultation") int idConsultation) {
        facadeApplication.confirmerRDV(idConsultation);
        return ResponseEntity.ok().body("La consultation n°"+idConsultation+" a bien été confirmée.");
    }

    @GetMapping("/medecin/{idMedecin}/consultations")
    public ResponseEntity<Collection<Consultation>> voirConsultationsMedecin(@PathVariable("idMedecin") int idMedecin) {
        List<Consultation> consultations = facadeApplication.voirConsultationsMedecin(idMedecin);
        return ResponseEntity.ok().body(consultations);
    }

    @PatchMapping("/consultation/{idConsultation}/compterendu")
    public ResponseEntity<String> modifierCR( @PathVariable("idConsultation") int idConsultation,@RequestBody String compterendu) {
        facadeApplication.modifierCRConsultation(idConsultation,compterendu);
        return ResponseEntity.ok().body("Le compte rendu pour la consultation n°"+idConsultation+" :\n est : "+compterendu);
    }
    @PostMapping("/consultation/nouveau")
    public ResponseEntity<Consultation> prendreRDV(@RequestBody String motif, @RequestBody String ordonnance, @RequestBody String dateRDV,@RequestBody String heureRDV, @RequestBody String type, Principal principal) {
        Patient patient = facadeApplication.getPatientByEmail(principal.getName());
        Consultation consultation = facadeApplication.prendreRDV(patient, dateRDV, heureRDV, motif, ordonnance, type);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idConsultation}").buildAndExpand(consultation.getId_cons()).toUri();
        return ResponseEntity.created(location).body(consultation);
    }

    // A modifier
    @PostMapping("/consultation/{idConsultation}/annulation")
    public ResponseEntity<String> annulationRDV(@PathVariable("idConsultation") int idConsultation, @RequestBody String motif) {
        facadeApplication.annulerConsultation(idConsultation, motif);
        return ResponseEntity.ok().body("Annulation de la consultation n°"+idConsultation+" - Motif : "+motif);
    }
}