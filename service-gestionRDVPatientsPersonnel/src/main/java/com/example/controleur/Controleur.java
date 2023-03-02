package com.example.controleur;

import com.example.exceptions.AdresseMailDejaUtiliseeException;
import com.example.facade.FacadeApplication;
import com.example.modele.Consultation;
import com.example.modele.Medecin;
import com.example.modele.Patient;
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
@RequestMapping(value = "/rdvpatients",produces = {MediaType.APPLICATION_JSON_VALUE})
public class Controleur {
    @Autowired
    FacadeApplication facadeApplication;

    @PostMapping("/medecin/nouveau")
    public ResponseEntity<?> ajouterMedecin(@RequestBody Medecin medecin) throws AdresseMailDejaUtiliseeException {
        int idMedecin = facadeApplication.ajouterMedecin(medecin.getPrenom_uti(), medecin.getNom_uti(),medecin.getEmail_uti());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idMedecin}").buildAndExpand(idMedecin).toUri();
        return ResponseEntity.created(location).body(medecin);
    }

    @PostMapping("/patient/nouveau")
    public ResponseEntity<?> ajouterPatient(@RequestBody Patient patient) throws AdresseMailDejaUtiliseeException {
        int idPatient = facadeApplication.ajouterPatient(patient.getPrenom_uti(), patient.getNom_uti(), patient.getEmail_uti(),patient.getNumsecu_pat(),patient.getNumtel_pat(), String.valueOf(patient.getDatenais_pat()), patient.getGenre_pat());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idPatient}").buildAndExpand(idPatient).toUri();
        return ResponseEntity.created(location).body(patient);
    }

    @PatchMapping("/personnel/modif/patient/{numSecuPatient}/antecedents")
    public ResponseEntity<String> modifierAntecedents( @PathVariable("numSecuPatient") String numSecuPatient, @RequestParam String antecedents) {
        facadeApplication.modifierAntecedentsPatient(numSecuPatient,antecedents);
        return ResponseEntity.ok().body("Nouveaux antécédents pour le patient n°"+numSecuPatient+" : \n"+antecedents);
    }

    @PatchMapping("/personnel/modif/patient/{numSecuPatient}/medecintraitant")
    public ResponseEntity<String> assignerMedecinTraitant( @PathVariable("numSecuPatient") String numSecuPatient, @RequestParam String prenomMedecin, @RequestParam String nomMedecin) {
        facadeApplication.assignerMedecinTraitant(numSecuPatient,nomMedecin,prenomMedecin);
        return ResponseEntity.ok().body("Nouveau médecin assigné au patient n°"+numSecuPatient+" : "+prenomMedecin+" "+nomMedecin);
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
}
