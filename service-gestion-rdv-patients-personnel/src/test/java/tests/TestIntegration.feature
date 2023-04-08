Feature: Tests Integration gestion rdv patients et personnels
  Background:
    * url 'http://localhost:8083/api/v1/rdvpatients'
    * header Accept = 'application/json'
    * header Authorization = 'Bearer ' + 'eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZnJlZEBob3BpdGFsLXNlY3JldGFpcmUuZnIiLCJleHAiOjE2ODA0ODkxMDcsImlhdCI6MTY4MDQ1MzEwNywic2NvcGUiOiJTRUNSRVRBSVJFIn0.I6HEMhVI1ZU_Mil7uRYds5VWT5pSdXXn6oNhDZhsnTniY-Uera3BYo2v4012KhALSJ9lmtc4St6HTbEJ8RjEcLYbLBIuhly-rufRxrtHbWexX6NqqVDpzHULB-uJg084ZDVcxZdV7aoLWT862BnLIDUdJFG9MF7WuJe3mZDOJWHtR38Vw5rAEYuvZRDezY1KcCgeW3PeFY-MjQDzmgxObNdfktdKYwddhhl2tpgCwem8xqlY5jwddU2AoA28ET_pdT24RHjtOMOwb28auSlYWCA-Eq9ESOYLviu0XLhYf7JUoSzR9z1yDgqc8TJnIRznn46UMJ3KktABswU_l1DAGA'

  Scenario: Test de creation de medecin
    Given path '/medecin'
    And request {"prenom": "Mirabelle", "nom": "Soubai", "email": "mirabelle31.soubai@etu.univ-orleans.fr" }
    When method post
    Then status 201
    And match response == { "id": "#number", "prenom": "Mirabelle", "nom": "Soubai", "email": "mirabelle31.soubai@etu.univ-orleans.fr","listeConsultations": [],"listePatients": []}
    And print response


  Scenario: Test de creation de medecin déjà existant
    Given path '/medecin'
    And request {"prenom": "Mirabelle", "nom": "Soubai", "email": "mirabelle.soubai@etu.univ-orleans.fr" }
    When method post
    Then status 409
    And match response == 'Cette adresse mail est déjà utilisée pour un autre médecin.'
    And print response


  Scenario: Test de creation de patient
    Given path '/patient'
    And request {"prenom": "Joel", "nom": "Dino", "email": "joel.dino@etu.univ-orleans.fr","numSecu": "154","numTel": "0123456789","dateNaissance": "14-02-2000","genre": "Homme"}
    When method post
    Then status 201
    And match response == { "id": "#number", "prenom": "Joel", "nom": "Dino", "email": "joel.dino@etu.univ-orleans.fr","numSecu": "154","numTel": "0123456789","dateNaissance": "14-02-2000","genre": "Homme","idMedecinTraitant":0,"antecedents":null}
    And print response

  Scenario: Test de creation de patient déjà existant
    Given path '/patient'
    And request  {"prenom": "Joel", "nom": "Dino", "email": "joel.dino@etu.univ-orleans.fr","numSecu": "154","numTel": "0123456789","dateNaissance": "14-02-2000","genre": "Homme"}
    When method post
    Then status 409
    And match response == 'Ce numéro de sécurité sociale est déjà attribué à un autre patient.'
    And print response


  Scenario: Test d'ajout des antécédents d'un patient à son profil
    Given path 'personnel/modif/patient/154/antecedents'
    And request {"antecedents": "Allergique"}
    When method patch
    Then status 200
    And match response == 'Les antécédents pour le patient n°154 : \nAllergique'
    And print response

  Scenario: Test pour voir les données d'un patient
    Given path '/patient/154'
    When method get
    Then status 200
    And match response == {"id":5,"prenom":"Joel","nom":"Dino","email":"joel.dino@etu.univ-orleans.fr","numSecu":"154","numTel":"0123456789","dateNaissance":"14-02-2000","genre":"Homme","idMedecinTraitant":'#number',"antecedents":"Allergique"}
    And print response


  Scenario: Test d'assigner un medecin à un patient
    Given path 'personnel/modif/patient/154/medecintraitant'
    And request {"prenom": "Mirabelle", "nom": "Soubai"}
    When method patch
    Then status 200
    And match response == 'Le médecin assigné au patient n°154 est Mirabelle Soubai'
    And print response

  Scenario: Test de creation de consultation
    Given path '/consultation'
    And request {"dateRDV": "17-07-2022","heureRDV": "13H20","motif": "Mal de crâne","type": "SOINS_DIVERS","ordonnance": "Doliprane 2 fois par jour"}
    When method post
    Then status 201
    And match response == {"id":"#number","creneau": {"date": "17-07-2022","heure":"13H20"},"type":"SOINS_DIVERS","motif":"Mal de crâne","compteRendu": null,"ordonnance": "Doliprane 2 fois par jour","idMedecin": "#number","idPatient": "#number","dateCreation":"2023-03-19","dateModification": "2023-03-19"}
    And print response

  Scenario: Test de creation de consultation déjà existante
    Given path '/consultation'
    And request {"dateRDV": "17-07-2022","heureRDV": "13H20","motif": "Mal de crâne","type": "SOINS_DIVERS","ordonnance": "Doliprane 2 fois par jour"}
    When method post
    Then status 409
    And match response == 'Ce creneau est déjà pris par un autre patient !'
    And print response

  Scenario: Test de confirmation de la consultation par le medecin
    Given path 'consultation/1/confirmer'
    When method patch
    Then status 200
    And match response == 'La consultation n°1 a bien été confirmée.'
    And print response

  Scenario: Test de confirmation de la consultation déjà confirmé par le medecin
    Given path 'consultation/1/confirmer'
    When method patch
    Then status 409
    And match response == 'Cette consultation est déjà confirmée !'
    And print response

  Scenario: Test d'ajout du compte rendu dans la consultation
    Given path 'consultation/1/compterendu'
    And request {"compteRendu":"Patient enrhumé"}
    When method patch
    Then status 200
    And match response == 'Le compte rendu pour la consultation n°1 :\n est : Patient enrhumé'
    And print response

  Scenario: Test pour voir les consultations d'un medecin
    Given path '/medecin/2/consultations'
    When method get
    Then status 200
    And print response

  Scenario: Test pour voir les consultations d'un medecin inexistant
    Given path '/medecin/9/consultations'
    When method get
    Then status 404
    And match response == 'Ce médecin n\'existe pas !'
    And print response

  Scenario: Test d'annulation d'un RDV
    Given path 'consultation/1/annulation'
    When method delete
    Then status 200
    And match response == 'Annulation de la consultation n°1'
    And print response

  Scenario: Test d'annulation d'un RDV inexistant
    Given path 'consultation/9/annulation'
    When method delete
    Then status 404
    And match response == 'Cette consultation n\'existe pas !'
    And print response

