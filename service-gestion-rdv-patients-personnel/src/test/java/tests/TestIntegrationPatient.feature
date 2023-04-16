Feature: Tests Integration des services du Patient
  Background:
    * url 'http://localhost:8083/api/v1/rdvpatients'
    * header Accept = 'application/json'
    * def signIn = call read('TestConnexionPatient.feature')
    * configure headers = { Authorization: '#(signIn.token)' },

  Scenario: Test pour voir les données d'un patient
    Given path '/patient/208'
    When method get
    Then status 200
    And print response

  Scenario: Test pour voir les données d un patient inexistant
    Given path '/patient/848'
    When method get
    And match response == "Ce patient n'existe pas !"
    Then status 404
    And print response

  Scenario: Test pour voir les données d un patient qui n’est pas le patient connecté
    Given path '/patient/888'
    When method get
    And match response == "Vous ne pouvez pas voir les données de ce patient"
    Then status 403
    And print response

  Scenario: Test de creation de consultation
    Given path '/consultation'
    And request {"dateRDV": "17-07-2022","heureRDV": "13H20","motif": "Mal de crâne","type": "SOINS_DIVERS"}
    When method post
    Then status 201
    And print response

  Scenario: Test de creation de consultation avec un creneau non disponible
    Given path '/consultation'
    And request {"dateRDV": "17-07-2022","heureRDV": "13H20","motif": "Mal de crâne","type": "SOINS_DIVERS"}
    When method post
    And match response == "Ce creneau n'est pas disponible !"
    Then status 409
    And print response

  Scenario: Test de creation de consultation avec un type de consultation inexistant
    Given path '/consultation'
    And request {"dateRDV": "17-07-2022","heureRDV": "13H20","motif": "Mal aux mains","type": "DERMATOLOGIE"}
    When method post
    And match response == "Ce type de consultation n'existe pas !"
    Then status 406
    And print response

  Scenario: Test d'annulation d'un RDV
    Given path 'consultation/1/annulation'
    When method delete
    Then status 202
    And print response

  Scenario: Test d'annulation d'un RDV inexistant
    Given path 'consultation/1/annulation'
    When method delete
    And match response == "Cette consultation n'existe pas !"
    Then status 404
    And print response

  Scenario: Test d'annulation d'un RDV d’un autre patient
    Given path 'consultation/5/annulation'
    When method delete
    And match response == "Vous n'êtes pas autorisé à annuler le RDV d'un autre patient !"
    Then status 403
    And print response

  Scenario: Test pour voir le medecin traitant d’un patient
    Given path 'patient/208/medecintraitant'
    When method get
    Then status 200
    And print response

  Scenario: Test pour voir le medecin traitant d’un patient autre que le patient connecté
    Given path 'patient/888/medecintraitant'
    When method get
    And match response == "Vous ne pouvez pas voir le médecin traitant de ce patient"
    Then status 403
    And print response


