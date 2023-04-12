Feature: Tests Integration des services du Secretaire
  Background:
    * url 'http://localhost:8083/api/v1/rdvpatients'
    * header Accept = 'application/json'
    * def signIn = call read('TestConnexionSecretaire.feature')
    * configure headers = { Authorization: '#(signIn.token)' },

  Scenario: Test creation d'un medecin
    Given path '/medecin'
    And request {"prenom": "med", "nom": "ecin", "email": "meederr.ecin@hopital-medecin.fr" }
    When method post
    Then status 201
    And print response

  Scenario: Test creation d'un medecin déjà existant
    Given path '/medecin'
    And request {"prenom": "med", "nom": "ecin", "email": "meederr.ecin@hopital-medecin.fr" }
    When method post
    Then status 409
    And print response

  Scenario: Test de creation de patient
    Given path '/patient'
    And request {"prenom": "pat", "nom": "ien", "email": "pat.ien@etu.univ-orleans.fr","numSecu": "154","numTel": "0123456789","dateNaissance": "14-02-2000","genre": "Homme"}
    When method post
    Then status 201
    And print response

  Scenario: Test de creation de patient avec un numéro de sécurité sociale déjà attribué
    Given path '/patient'
    And request {"prenom": "pat", "nom": "ien", "email": "patient2@etu.univ-orleans.fr","numSecu": "154","numTel": "0123456789","dateNaissance": "14-02-2000","genre": "Homme"}
    When method post
    Then status 409
    And print response

  Scenario: Test de creation de patient avec un mail déjà utilisée
    Given path '/patient'
    And request {"prenom": "pat", "nom": "ien", "email": "pat.ien@etu.univ-orleans.fr","numSecu": "124","numTel": "0123456789","dateNaissance": "14-02-2000","genre": "Homme"}
    When method post
    Then status 409
    And print response

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


  Scenario: Test d'assigner un medecin à un patient
    Given path 'personnel/modif/patient/154/medecintraitant'
    And request {"prenom": "med", "nom": "ecin"}
    When method patch
    Then status 202
    And print response

  Scenario: Test d assigner un medecin inexistant à un patient
    Given path 'personnel/modif/patient/154/medecintraitant'
    And request {"prenom": "ufhuirh", "nom": "zudhuh"}
    When method patch
    Then status 404
    And print response

  Scenario: Test d assigner un medecin à un patient inexistant
    Given path 'personnel/modif/patient/5487/medecintraitant'
    And request {"prenom": "med", "nom": "ecin"}
    When method patch
    Then status 404
    And print response

  Scenario: Test d assigner un medecin à un patient étant déjà assigné au medecin
    Given path 'personnel/modif/patient/154/medecintraitant'
    And request {"prenom": "med", "nom": "ecin"}
    When method patch
    Then status 409
    And print response

  Scenario: Test pour voir les consultations d'un medecin
    Given path '/medecin/12/consultations'
    When method get
    Then status 200
    And print response

  Scenario: Test pour voir les consultations d'un medecin inexistant
    Given path '/medecin/999/consultations'
    When method get
    Then status 404
    And print response

