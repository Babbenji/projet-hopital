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

  Scenario: Test pour voir les données d un patient inexistant
    Given path '/patient/245'
    When method get
    Then status 404
    And print response

  Scenario: Test d'ajout des antécédents d'un patient à son profil
    Given path 'personnel/modif/patient/208/antecedents'
    And request {"antecedents": "Allergique"}
    When method patch
    Then status 202
    And print response

  Scenario: Test d ajout des antécédents d un patient inexistant
    Given path 'personnel/modif/patient/999/antecedents'
    And request {"antecedents": "Allergique"}
    When method patch
    Then status 404
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

  Scenario: Test de confirmation de la consultation inexistante
    Given path 'consultation/7/confirmer'
    When method patch
    Then status 404
    And print response


  Scenario: Test d ajout du compte rendu dans la consultation
    Given path 'consultation/1/compterendu'
    And request {"compteRendu":"Patient enrhumé"}
    When method patch
    Then status 202
    And print response

  Scenario: Test d ajout du compte rendu dans une consultation inexistante
    Given path 'consultation/898/compterendu'
    And request {"compteRendu":"Patient enrhumé"}
    When method patch
    Then status 404
    And print response

  Scenario: Test d ajout du compte rendu dans une consultation non confirmé par un medecin
    Given path 'consultation/4/compterendu'
    And request {"compteRendu":"Patient enrhumé"}
    When method patch
    Then status 409
    And print response