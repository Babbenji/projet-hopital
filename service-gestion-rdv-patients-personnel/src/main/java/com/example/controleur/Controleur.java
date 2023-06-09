package com.example.controleur;

import com.example.exceptions.*;
import com.example.modele.*;
import com.example.facade.FacadeApplication;
import com.example.modele.DTO.ConsultationDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/rdvpatients",produces = {MediaType.APPLICATION_JSON_VALUE})
public class Controleur {

    @Autowired
    FacadeApplication facadeApplication;

    @Operation(summary = "Permet d'ajouter un médecin")
    @PostMapping("/medecin")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<?> ajouterMedecin(@RequestBody Medecin medecin){
        try {
            Medecin nouveauMedecin = facadeApplication.ajouterMedecin(
                    medecin.getPrenom(),
                    medecin.getNom(),
                    medecin.getEmail()
            );
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idMedecin}").buildAndExpand(medecin.getId()).toUri();
            return ResponseEntity.created(location).body(nouveauMedecin);
        } catch (AdresseMailDejaUtiliseeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cette adresse mail est déjà utilisée pour un autre médecin.");
        }
    }
    @Operation(summary = "Permet d'ajouter un patient")
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
        } catch (AdresseMailDejaUtiliseeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cette adresse mail est déjà utilisée pour un autre patient.");
        }
    }
    @Operation(summary = "Permet de récupérer les informations d'un patient")
    @GetMapping("/patient/{numSecu}")
    @PreAuthorize("hasAnyAuthority('SCOPE_MEDECIN', 'SCOPE_PATIENT', 'SCOPE_SECRETAIRE')")
    public ResponseEntity<?> afficherPatient(@PathVariable("numSecu") String numSecu, Principal principal){
        try {
            Patient patient = facadeApplication.getPatientByNumSecu(numSecu);
            if (principal.getName().contains("@hopital")) {
                return ResponseEntity.ok(patient);
            }else{
                if (patient.getEmail().equals(principal.getName())) {
                    return ResponseEntity.ok(patient);
                }else{
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vous ne pouvez pas voir les données de ce patient");
                }
            }
        } catch (PatientInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce patient n'existe pas !");
        }
    }
    @Operation(summary = "Permet de modifier les antécédents d'un patient")
    @PatchMapping("/personnel/modif/patient/{numSecu}/antecedents")
    @PreAuthorize("hasAuthority('SCOPE_MEDECIN')")
    public ResponseEntity<?> modifierAntecedents(@PathVariable("numSecu") String numSecu, @RequestBody Patient patient) {
        try {
            facadeApplication.modifierAntecedentsPatient(numSecu,patient.getAntecedents());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Les antécédents pour le patient n°"+numSecu+" : \n"+patient.getAntecedents());
        } catch (PatientInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce patient n'existe pas !");
        }
    }
    @Operation(summary = "Permet d'assigner un médecin traitant à un patient")
    @PatchMapping("/personnel/modif/patient/{numSecu}/medecintraitant")
    @PreAuthorize("hasAnyAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<?> assignerMedecinTraitant(@PathVariable("numSecu") String numSecu, @RequestBody Medecin medecin) {
        try {
            facadeApplication.assignerMedecinTraitant(numSecu,medecin.getPrenom(),medecin.getNom());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Le médecin assigné au patient n°"+numSecu+" est "+medecin.getPrenom()+" "+medecin.getNom());
        } catch (PatientInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce patient n'existe pas !");
        } catch (MedecinInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce médecin n'existe pas !");
        } catch (PatientDejaAttribueAuMedecinException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ce patient est déjà assigné au médecin !");
        }
    }
    @Operation(summary = "Permet à un patient de prendre un rendez-vous")
    @PostMapping("/consultation")
    @PreAuthorize("hasAuthority('SCOPE_PATIENT')")
    public ResponseEntity<?> prendreRDV(@RequestBody ConsultationDTO consultationDTO, Principal principal){
        try {
            Patient patient = facadeApplication.getPatientByEmail(principal.getName());
            Consultation consultation = facadeApplication.prendreRDV(patient,consultationDTO.getDateRDV(),consultationDTO.getHeureRDV(),consultationDTO.getMotif(),consultationDTO.getType());
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idConsultation}").buildAndExpand(consultation.getId()).toUri();
            return ResponseEntity.created(location).body(consultation);
        } catch (TypeConsultationInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Ce type de consultation n'existe pas !");
        } catch (CreneauIndisponibleException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ce creneau n'est pas disponible !");
        } catch (PasDeMedecinTraitantAssigneException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veuillez définir votre médecin traitant avant de prendre RDV !");
        } catch (PatientInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vous n'êtes pas enregistré dans notre base de données, veuillez contacter la secrétaire de l'hopital !");
        }
    }
    @Operation(summary = "Permet de confirmer la réservation d'une consultation")
    @PatchMapping("/consultation/{idConsultation}/confirmer")
    @PreAuthorize("hasAuthority('SCOPE_MEDECIN')")
    public ResponseEntity<?> confirmerRDV( @PathVariable("idConsultation") int idConsultation) {
        try {
            facadeApplication.confirmerRDV(idConsultation);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("La consultation n°"+idConsultation+" a bien été confirmée.");
        } catch (ConsultationInexistanteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cette consultation n'existe pas !");
        } catch (ConsultationDejaConfirmeeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cette consultation est déjà confirmée !");
        }
    }
    @Operation(summary = "Permet d'éditer le compte-rendu d'une consultation")
    @PatchMapping("/consultation/{idConsultation}/compterendu")
    @PreAuthorize("hasAuthority('SCOPE_MEDECIN')")
    public ResponseEntity<?> modifierCR( @PathVariable("idConsultation") int idConsultation, @RequestBody Consultation consultation) {
        try {
            facadeApplication.modifierCRConsultation(idConsultation,consultation.getCompteRendu(), consultation.getListeProduitsMedicaux());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Le compte rendu pour la consultation n°"+idConsultation+" :\n est : "+consultation.getCompteRendu());
        } catch (ConsultationInexistanteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cette consultation n'existe pas !");
        } catch (ConsultationNonConfirmeeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Veuillez confirmer la consultation avant de pouvoir mettre à jour son compte-rendu !");
        }
    }
    @Operation(summary = "Permet de voir les consultations d'un médecin")
    @GetMapping("/medecin/{idMedecin}/consultations")
    @PreAuthorize("hasAnyAuthority('SCOPE_MEDECIN','SCOPE_SECRETAIRE')")
    public ResponseEntity<?> voirConsultationsMedecin(@PathVariable("idMedecin") int idMedecin, Principal principal) {
        try {
            if (principal.getName().contains("@hopital-medecin.fr")) {
                Medecin medecinConnecte = facadeApplication.getMedecinByEmail(principal.getName());
                Medecin medecinConsulte = facadeApplication.getMedecinByID(idMedecin);
                if (medecinConsulte.getId() == medecinConnecte.getId()) {
                    List<Consultation> consultations = facadeApplication.voirConsultationsMedecin(idMedecin);
                    return ResponseEntity.ok().body(consultations);
                }else{
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vous ne pouvez pas voir les consultations d'un autre médecin");
                }
            }else{
                List<Consultation> consultations = facadeApplication.voirConsultationsMedecin(idMedecin);
                return ResponseEntity.ok().body(consultations);
            }
        } catch (MedecinInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce médecin n'existe pas !");
        } catch (ConsultationInexistanteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le medecin a une consultation qui n'existe pas !");
        } catch (PasDeConsultationAssigneAuMedecinException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune consultation trouvée pour ce médecin !");
        }
    }
    @Operation(summary = "Permet d'annuler une consultation")
    @DeleteMapping("/consultation/{idConsultation}/annulation")
    @PreAuthorize("hasAuthority('SCOPE_PATIENT')")
    public ResponseEntity<?> annulationRDV(@PathVariable("idConsultation") int idConsultation, Principal principal) {
        try {
            Patient patientConnecte = facadeApplication.getPatientByEmail(principal.getName());
            facadeApplication.annulerConsultation(idConsultation, patientConnecte.getId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Annulation de la consultation n°"+idConsultation);
        } catch (ConsultationInexistanteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cette consultation n'existe pas !");
        } catch (PatientInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vous n'êtes pas enregistré dans notre base de données, veuillez contacter la secrétaire de l'hopital !");
        } catch (PatientConnecteDifferentPatientConsultationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vous n'êtes pas autorisé à annuler le RDV d'un autre patient !");
        }
    }
    @Operation(summary = "Récupère le médecin traitant d'un patient")
    @GetMapping("/patient/{numSecu}/medecintraitant")
    @PreAuthorize("hasAnyAuthority('SCOPE_MEDECIN', 'SCOPE_PATIENT', 'SCOPE_SECRETAIRE')")
    public ResponseEntity<?> afficherMedecinTraitantPatient(@PathVariable("numSecu") String numSecu, Principal principal){
        try {
            if (principal.getName().contains("@hopital")) {
                Medecin medecinTraitant = facadeApplication.getMedecinTraitant(numSecu);
                return ResponseEntity.ok(medecinTraitant);
            }else{
                Patient patient = facadeApplication.getPatientByNumSecu(numSecu);
                if (patient.getEmail().equals(principal.getName())){
                    Medecin medecinTraitant = facadeApplication.getMedecinTraitant(patient.getNumSecu());
                    return ResponseEntity.ok(medecinTraitant);
                }else{
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vous ne pouvez pas voir le médecin traitant de ce patient");
                }
            }
        } catch (PatientInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce patient n'existe pas !");
        } catch (PasDeMedecinTraitantAssigneException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pas de médecin traitant assigné à ce patient");
        }
    }
    @Operation(summary = "Récupère les produits médicaux utiisés lors d'une consultation")
    @GetMapping("/consultation/{idConsultation}/produits")
    @PreAuthorize("hasAnyAuthority('SCOPE_MEDECIN', 'SCOPE_SECRETAIRE')")
    public ResponseEntity<?> afficherProduitsConsultation(@PathVariable("idConsultation") int idConsultation){
        try {
            Map<String, Integer> produitsConsultation = facadeApplication.voirProduitsConsultation(idConsultation);
            return ResponseEntity.ok(produitsConsultation);
        } catch (ConsultationInexistanteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cette consultation n'existe pas !");
        }
    }
    @Operation(summary = "Permet de récupérer les patients d'un médecin")
    @GetMapping("/medecin/{idMedecin}/patients")
    @PreAuthorize("hasAnyAuthority('SCOPE_MEDECIN', 'SCOPE_SECRETAIRE')")
    public ResponseEntity<?> voirPatientsMedecin(@PathVariable("idMedecin") int idMedecin, Principal principal){
        try {
            if (principal.getName().contains("@hopital-medecin.fr")) {
                Medecin medecinConnecte = facadeApplication.getMedecinByEmail(principal.getName());
                Medecin medecinConsulte = facadeApplication.getMedecinByID(idMedecin);
                if (medecinConsulte.getId() == medecinConnecte.getId()) {
                    Collection<Patient> listePatients = facadeApplication.voirTousLesPatientsMedecin(idMedecin);
                    return ResponseEntity.ok().body(listePatients);
                }else{
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vous ne pouvez pas voir les patients de ce médecin");
                }
            }else{
                Collection<Patient> listePatients = facadeApplication.voirTousLesPatientsMedecin(idMedecin);
                return ResponseEntity.ok().body(listePatients);
            }
        } catch (MedecinInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce médecin n'existe pas !");
        } catch (MedecinSansPatientException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce medecin n'a pas de patient assigné !");
        }
    }
    @Operation(summary = "Permet de récupérer toutes les consultations")
    @GetMapping("/consultation/liste")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<?> afficherToutesLesConsultations(){
        Collection<Consultation> listeConsultations = facadeApplication.getAllConsultations();
        return ResponseEntity.ok(listeConsultations);
    }
    @Operation(summary = "Permet de récupérer les consultations par type")
    @GetMapping("/consultation/liste/{type}")
    @PreAuthorize("hasAuthority('SCOPE_SECRETAIRE')")
    public ResponseEntity<?> afficherToutesLesConsultationsParType(@PathVariable("type") String type){
        try {
            Collection<Consultation> listeConsultations = facadeApplication.getAllConsultationsParType(type);
            return ResponseEntity.ok(listeConsultations);
        } catch (TypeConsultationInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Ce type de consultation n'existe pas !");
        }
    }
}