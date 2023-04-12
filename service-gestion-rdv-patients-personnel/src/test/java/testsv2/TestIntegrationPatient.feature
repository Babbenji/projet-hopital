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

  Scenario: Test de creation de consultation
    Given path '/consultation'
    And request {"dateRDV": "17-07-2022","heureRDV": "13H20","motif": "Mal de crâne","type": "SOINS_DIVERS"}
    When method post
    Then status 201
    And print response

  Scenario: Test de creation de consultation déjà existante
    Given path '/consultation'
    And request {"dateRDV": "17-07-2022","heureRDV": "13H20","motif": "Mal de crâne","type": "SOINS_DIVERS"}
    When method post
    Then status 409
    And print response