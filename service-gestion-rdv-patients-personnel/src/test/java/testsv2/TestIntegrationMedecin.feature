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

  Scenario: Test de confirmation de la consultation déjà confirmée par le medecin
    Given path 'consultation/1/confirmer'
    When method patch
    Then status 409
    And print response

  Scenario: Test pour voir les consultations d’un medecin
    Given path '/medecin/12/consultations'
    When method get
    Then status 200
    And print response
  Scenario: Test pour voir les consultations d’un medecin autre que celui avec lequel je suis connecté
    Given path '/medecin/14/consultations'
    When method get
    Then status 403
    And print response
  Scenario: Test pour voir les consultations d’un medecin inexistant
    Given path '/medecin/5/consultations'
    When method get
    Then status 404
    And print response

  Scenario: Test pour voir le medecin traitant d’un patient
    Given path 'patient/208/medecintraitant'
    When method get
    Then status 200
    And print response
  Scenario: Test pour voir le medecin traitant d’un patient inexistant
    Given path 'patient/9/medecintraitant'
    When method get
    Then status 404
    And print response
  Scenario: Test pour voir le medecin traitant d’un patient n’ayant pas de médecin traitant assigné
    Given path 'patient/888/medecintraitant'
    When method get
    Then status 404
    And print response

  Scenario: Test pour voir les produits utilisés lors d’une consultation
    Given path 'consultation/5/produits'
    When method get
    Then status 200
    And print response
  Scenario: Test pour voir les produits utilisés lors d’une consultation inexistante
    Given path 'consultation/10/produits'
    When method get
    Then status 404
    And print response

  Scenario: Test pour voir les patients assignés à un médecin
    Given path 'medecin/12/patients'
    When method get
    Then status 200
    And print response
  Scenario: Test pour voir les patients assignés à un médecin qui n’est pas le medecin connecté
    Given path 'medecin/14/patients'
    When method get
    Then status 403
    And print response
  Scenario: Test pour voir les patients assignés à un médecin inexistant
    Given path 'medecin/1/patients'
    When method get
    Then status 404
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