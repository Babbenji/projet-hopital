#!/bin/bash

# Lancement du shell mongo
docker exec -tt bdd_rdv_patients mongosh

# Sélection de la base de données
use rdvpatients

# Insertion des patients
db.patient.insert({_id : 9, prenom : "Rachelle", nom : "Juza", email : "userpat@outlook.fr", numSecu : "208", numTel : "2654615634", dateNaissance :"04-08-2000", genre : "Femme", idMedecinTraitant : 12})

db.patient.insert({_id : 18, prenom : "Olivier", nom : "Giroud", email : "oliviergigi@outlook.fr", numSecu : "125", numTel : "0123456789", dateNaissance :"24-12-1992", genre : "Homme", idMedecinTraitant : 12})

db.patient.insert({_id : 20, prenom : "Juan", nom : "Delafisa", email : "juanito@outlook.fr", numSecu : "888", numTel : "9876543210", dateNaissance :"24-06-1995", genre : "Homme", idMedecinTraitant : 0})

# Insertion des consultations
db.consultation.insert({_id : 1, creneau : {"_id" : 1, "date" :"26-02-2022", "heure" : "18H10", "disponibilite" : Boolean(false)},  type : "SOINS_DIVERS", motif : "Mal de crâne", confirmation : Boolean(false), "idMedecin" : 12, idPatient : 9, dateCreation : "2023-04-10", "dateModification" : "2023-04-10", listeProduitMedicaux : Object()})

db.consultation.insert({_id : 4, creneau : {"_id" : 2, "date" :"27-02-2022", "heure" : "13H", "disponibilite" : Boolean(false)},  type : "DENTAIRE", motif : "Retrait d'une carie", confirmation : Boolean(false), "idMedecin" : 12, idPatient : 9, dateCreation : "2023-04-10", "dateModification" : "2023-04-10", listeProduitMedicaux : Object()})

db.consultation.insert({_id : 5, creneau : {"_id" : 3, "date" :"27-02-2022", "heure" : "15H", "disponibilite" : Boolean(false)},  type : "OPTIQUE", motif : "Opération de l'iris", confirmation : Boolean(true), "idMedecin" : 12, idPatient : 18, dateCreation : "2023-04-10", "dateModification" : "2023-04-10", listeProduitMedicaux : Object()})

# Insertion des médecins
db.medecin.insert({_id : 12, prenom : "Jean", nom : "Luc", email : "usermed@hopital-medecin.fr", listeConsultations : Array(1,4,5), listePatients : Array(154, 9, 18)})

EOF
