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
    Then status 404
    And print response

  Scenario: Test pour voir les données d un patient qui n’est pas le patient connecté
    Given path '/patient/888'
    When method get
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
    Then status 409
    And print response

  Scenario: Test de creation de consultation avec un type de consultation inexistant
    Given path '/consultation'
    And request {"dateRDV": "17-07-2022","heureRDV": "13H20","motif": "Mal aux mains","type": "DERMATOLOGIE"}
    When method post
    Then status 406
    And print response
