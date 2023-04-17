Feature: CAS D'USAGES

  Scenario: PRISE DE RDV CLASSIQUE

    Given url 'http://localhost:8080/api/auth/inscription'
    And request { "email": "brosseau.aaron@gmail.com", "password": "pwd123" }
    When method post
    Then status 201

    Given url 'http://localhost:8080/api/auth/inscription'
    And request { "email": "usersecr@hopital-secretaire.fr", "password": "pwd123" }
    When method post
    Then status 201

    Given url 'http://localhost:8081/api/v1/auth/inscription'
    And request { "email": "usermed@hopital-medecin.fr", "password": "pwd123" }
    When method post
    Then status 201



    Given url 'http://localhost:8080/api/auth/connexion'
    And request { "email": "brosseau.aaron@gmail.com", "password": "pwd123" }
    When method post
    Then status 200
    And def tokenPatient = response.token

    Given url 'http://localhost:8080/api/auth/connexion'
    And request { "email": "usersecr@hopital-secretaire.fr", "password": "pwd123" }
    When method post
    Then status 200
    And def tokenSecretaire = response.token

    Given url 'http://localhost:8080/api/auth/connexion'
    And request { "email": "usermed@hopital-medecin.fr", "password": "pwd123" }
    When method post
    Then status 200
    And def tokenMedecin = response.token




    Given url 'http://localhost:8080/rdvpatients/patient'
    And header Authorization = ''+tokenSecretaire
    And request {"prenom": "Aaron", "nom": "Brosseau", "email": "brosseau.aaron@gmail.com","numSecu": "1000224145555","numTel": "0123456789","dateNaissance": "14-02-2000","genre": "Homme"}
    When method post
    Then status 201

    Given url 'http://localhost:8080/rdvpatients/personnel/modif/patient/1000224145555/medecintraitant'
    And header Authorization = ''+tokenSecretaire
    And request {"prenom": "Jean", "nom": "Luc"}
    When method patch
    Then status 202

    Given url 'http://localhost:8080/rdvpatients/consultation'
    And header Authorization = ''+tokenPatient
    And request {"dateRDV": "18-04-2023","heureRDV": "16H30","motif": "Mal de crâne","type": "SOINS_DIVERS"}
    When method post
    Then status 201

    Given url 'http://localhost:8080/rdvpatients/consultation/1/confirmer'
    And header Authorization = ''+tokenMedecin
    When method patch
    Then status 202

    Given url 'http://localhost:8080/rdvpatients/consultation/1/compterendu'
    And header Authorization = ''+tokenMedecin
    And request {"compteRendu":"Patient fiévreux", "listeProduitsMedicaux" : {"Doliprane":1 ,"Pansement":2}}
    When method patch
    Then status 202

  Scenario: REAPPROVISIONNEMENT DE PRODUIT MEDICAL