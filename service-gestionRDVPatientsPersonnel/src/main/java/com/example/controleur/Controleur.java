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
    public ResponseEntity<Medecin> ajouterMedecin(@RequestBody Medecin medecin) throws AdresseMailDejaUtiliseeException {
        int idMedecin = facadeApplication.ajouterMedecin(medecin.getPrenom_uti(), medecin.getNom_uti(),medecin.getEmail_uti());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idMedecin}").buildAndExpand(idMedecin).toUri();
        return ResponseEntity.created(location).body(medecin);
    }

    @PostMapping("/patient/nouveau")
    public ResponseEntity<Patient> ajouterPatient(@RequestBody Patient patient) throws AdresseMailDejaUtiliseeException {
        int idPatient = facadeApplication.ajouterPatient(
                patient.getPrenom_uti(),
                patient.getNom_uti(),
                patient.getEmail_uti(),
                patient.getNumsecu_pat(),
                patient.getNumtel_pat(),
                String.valueOf(patient.getDatenais_pat()),
                patient.getGenre_pat());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idPatient}").buildAndExpand(idPatient).toUri();
        return ResponseEntity.created(location).body(patient);
    }

    @PatchMapping("/personnel/modif/patient/{numSecuPatient}/antecedents")
    public ResponseEntity<String> modifierAntecedents( @PathVariable("numSecuPatient") String numSecuPatient, @RequestBody String antecedents) {
        facadeApplication.modifierAntecedentsPatient(numSecuPatient,antecedents);
        return ResponseEntity.ok().body("Nouveaux antécédents pour le patient n°"+numSecuPatient+" : \n"+antecedents);
    }

    @PatchMapping("/personnel/modif/patient/{numSecuPatient}/medecintraitant")
    public ResponseEntity<String> assignerMedecinTraitant( @PathVariable("numSecuPatient") String numSecuPatient, @RequestBody String prenomMedecin, @RequestBody String nomMedecin) {
        facadeApplication.assignerMedecinTraitant(numSecuPatient,nomMedecin,prenomMedecin);
        return ResponseEntity.ok().body("Nouveau médecin assigné au patient n°"+numSecuPatient+"\n"+prenomMedecin+" "+nomMedecin);
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
        return ResponseEntity.ok().body("Nouveau compte rendu pour la consultation n°"+idConsultation+" :\n"+compterendu);
    }
    @PostMapping("/consultation/nouveau")
    public ResponseEntity<Consultation> prendreRDV(@RequestBody String motif, @RequestBody String ordonnance, @RequestBody String dateRDV,@RequestBody String heureRDV, @RequestBody String type, Principal principal) {
        Patient patient = facadeApplication.getPatientByEmail(principal.getName());
        Consultation consultation = facadeApplication.prendreRDV(patient, dateRDV, heureRDV, motif, ordonnance, type);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idConsultation}").buildAndExpand(consultation.getId_cons()).toUri();
        return ResponseEntity.created(location).body(consultation);
    }
    @PostMapping("/consultation/{idConsultation}/demandeannulation")
    public ResponseEntity<String> demandeAnnulation(@PathVariable("idConsultation") int idConsultation, @RequestBody String motif) {
        facadeApplication.demanderAnnulation(idConsultation, motif);
        return ResponseEntity.ok().body("Demande d'annulation effectué pour la consultation n°"+idConsultation);
    }
}