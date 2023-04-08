package com.example.controleur;

import com.example.exceptions.*;
import com.example.modele.*;
import com.example.facade.FacadeApplication;
import com.example.modele.DTO.ConsultationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/rdvpatients",produces = {MediaType.APPLICATION_JSON_VALUE})
public class Controleur {

    @Autowired
    FacadeApplication facadeApplication;

    @PostMapping("/medecin")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<?> ajouterMedecin(@RequestBody Medecin medecin){
        try {
            Medecin nouveauMedecin = facadeApplication.ajouterMedecin(medecin.getPrenom(), medecin.getNom(), medecin.getEmail());
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idMedecin}").buildAndExpand(medecin.getId()).toUri();
            return ResponseEntity.created(location).body(nouveauMedecin);
        } catch (AdresseMailDejaUtiliseeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cette adresse mail est déjà utilisée pour un autre médecin.");
        }
    }
    @PostMapping("/patient")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<?> ajouterPatient(@RequestBody Patient patient){
        try {
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
        } catch (NumeroSecuDejaAttribueException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ce numéro de sécurité sociale est déjà attribué à un autre patient.");
        }
    }

    @GetMapping("/patient/{numSecu}")
    @PreAuthorize("hasAnyAuthority('SCOPE_MEDECIN', 'SCOPE_SECRETAIRE')")
    public ResponseEntity<?> afficherPatient(@PathVariable("numSecu") String numSecu){
        try {
            Patient patient = facadeApplication.getPatientByNumSecu(numSecu);
            return ResponseEntity.ok(patient);
        } catch (PatientInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce patient n'existe pas !");
        }
    }

    @PatchMapping("/personnel/modif/patient/{numSecu}/antecedents")
    @PreAuthorize("hasAnyAuthority('SCOPE_MEDECIN', 'SCOPE_SECRETAIRE')")
    public ResponseEntity<String> modifierAntecedents(@PathVariable("numSecu") String numSecu, @RequestBody Patient patient) {
        try {
            facadeApplication.modifierAntecedentsPatient(numSecu,patient.getAntecedents());
            return ResponseEntity.ok().body("Les antécédents pour le patient n°"+numSecu+" : \n"+patient.getAntecedents());
        } catch (PatientInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce patient n'existe pas !");
        }
    }

    @PatchMapping("/personnel/modif/patient/{numSecu}/medecintraitant")
    @PreAuthorize("hasAnyAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<String> assignerMedecinTraitant(@PathVariable("numSecu") String numSecu, @RequestBody Medecin medecin) {
        try {
            facadeApplication.assignerMedecinTraitant(numSecu,medecin.getPrenom(),medecin.getNom());
            return ResponseEntity.ok().body("Le médecin assigné au patient n°"+numSecu+" est "+medecin.getPrenom()+" "+medecin.getNom());
        } catch (PatientInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce patient n'existe pas !");
        } catch (MedecinInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce médecin n'existe pas !");
        }
    }

    @PostMapping("/consultation")
    @PreAuthorize("hasAnyAuthority('SCOPE_PATIENT', 'SCOPE_SECRETAIRE')")
    public ResponseEntity<?> prendreRDV(@RequestBody ConsultationDTO consultationDTO){
        Patient patient;
        try {
            patient = facadeApplication.getPatientByEmail("brosseau.aaron@gmail.com");
            //patient = facadeApplication.getPatientByEmail(principal.getName());
        } catch (PatientInexistantException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vous n'êtes pas connecté !");
        }
        try {
            Consultation nouvelleConsultation = facadeApplication.prendreRDV(patient,consultationDTO.getDateRDV(),consultationDTO.getHeureRDV(),consultationDTO.getMotif(),consultationDTO.getOrdonnance(),consultationDTO.getType());
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idConsultation}").buildAndExpand(nouvelleConsultation.getId()).toUri();
            return ResponseEntity.created(location).body(nouvelleConsultation);
        } catch (TypeConsultationInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Ce type de consultation n'existe pas !");
        } catch (CreneauIndisponibleException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ce creneau est déjà pris par un autre patient !");
        } catch (PasDeMedecinTraitantAssigneException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veuillez définir votre médecin traitant avant de prendre RDV !");
        }
    }

    @PatchMapping("/consultation/{idConsultation}/confirmer")
    @PreAuthorize("hasAnyAuthority('SCOPE_MEDECIN', 'SCOPE_SECRETAIRE')")
    public ResponseEntity<String> confirmerRDV( @PathVariable("idConsultation") int idConsultation) {
        try {
            facadeApplication.confirmerRDV(idConsultation);
            return ResponseEntity.ok().body("La consultation n°"+idConsultation+" a bien été confirmée.");
        } catch (ConsultationInexistanteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cette consultation n'existe pas !");
        } catch (ConsultationDejaConfirmeeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cette consultation est déjà confirmée !");
        }
    }

    @PatchMapping("/consultation/{idConsultation}/compterendu")
    @PreAuthorize("hasAnyAuthority('SCOPE_MEDECIN', 'SCOPE_SECRETAIRE')")
    public ResponseEntity<String> modifierCR( @PathVariable("idConsultation") int idConsultation, @RequestBody Consultation consultation) {
        try {
            Map<String, Integer> map = new HashMap<>();
            for (String couple:consultation.getListeProduitsMedicaux()) {
                String[] keyValue = couple.split(":");
                String key = keyValue[0];
                Integer value = Integer.valueOf(keyValue[1]);
                map.put(key, value);
            }
            facadeApplication.modifierCRConsultation(idConsultation,consultation.getCompteRendu(), consultation.getListeProduitsMedicaux());
            return ResponseEntity.ok().body("Le compte rendu pour la consultation n°"+idConsultation+" :\n est : "+consultation.getCompteRendu());
        } catch (ConsultationInexistanteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cette consultation n'existe pas !");
        }
    }

    @GetMapping("/medecin/{idMedecin}/consultations")
    @PreAuthorize("hasAnyAuthority('SCOPE_MEDECIN','SCOPE_SECRETAIRE')")
    public ResponseEntity<?> voirConsultationsMedecin(@PathVariable("idMedecin") int idMedecin) {
        List<Consultation> consultations = null;
        try {
            consultations = facadeApplication.voirConsultationsMedecin(idMedecin);
        } catch (MedecinInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce médecin n'existe pas !");
        } catch (ConsultationInexistanteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le medecin a une consultation qui n'existe pas !");
        } catch (PasDeConsultationAssigneAuMedecinException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune consultation trouvée pour ce médecin !");
        }
        return ResponseEntity.ok().body(consultations);
    }

    @DeleteMapping("/consultation/{idConsultation}/annulation")
    @PreAuthorize("hasAnyAuthority('SCOPE_PATIENT', 'SCOPE_SECRETAIRE')")
    public ResponseEntity<String> annulationRDV(@PathVariable("idConsultation") int idConsultation) {
        try {
            facadeApplication.annulerConsultation(idConsultation);
            return ResponseEntity.ok().body("Annulation de la consultation n°"+idConsultation);
        } catch (MedecinInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce médecin n'existe pas !");
        } catch (ConsultationInexistanteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cette consultation n'existe pas !");
        }
    }
}