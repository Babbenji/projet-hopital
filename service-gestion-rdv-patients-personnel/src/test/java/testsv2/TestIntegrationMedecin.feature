Feature: Tests Integration des services du medecin
  Background:
    * url 'http://localhost:8083/api/v1/rdvpatients'
    * header Accept = 'application/json'
    * def signIn = call read('TestConnexionMedecin.feature')
    * configure headers = { Authorization: '#(signIn.token)' },

  Scenario: Test pour voir les données d'un patient
    Given path '/patient/208'
    When method get
    Then status 200
    And print response

  Scenario: Test d'ajout des antécédents d'un patient à son profil
    Given path 'personnel/modif/patient/208/antecedents'
    And request {"antecedents": "Allergique"}
    When method patch
    Then status 202
    And print response

  Scenario: Test de confirmation de la consultation par le medecin
    Given path 'consultation/1/confirmer'
    When method patch
    Then status 202
    And print response

  Scenario: Test de confirmation de la consultation déjà confirmé par le medecin
    Given path 'consultation/1/confirmer'
    When method patch
    Then status 409
    And print response
