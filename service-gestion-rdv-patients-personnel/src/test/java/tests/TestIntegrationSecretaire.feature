Feature: Tests Integration des services du Secretaire
  Background:
    * url 'http://localhost:8083/api/v1/rdvpatients'
    * header Accept = 'application/json'
    * def signIn = call read('TestConnexionSecretaire.feature')
    * configure headers = { Authorization: '#(signIn.token)' },

  Scenario: Test creation d'un medecin
    Given path '/medecin'
    And request {"prenom": "med", "nom": "ecin", "email": "meed.ecin@hopital-medecin.fr" }
    When method post
    Then status 201
    And print response

  Scenario: Test creation d'un medecin déjà existant
    Given path '/medecin'
    And request {"prenom": "med", "nom": "ecin", "email": "meed.ecin@hopital-medecin.fr" }
    When method post
    And match response == "Cette adresse mail est déjà utilisée pour un autre médecin."
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
    And match response == "Ce numéro de sécurité sociale est déjà attribué à un autre patient."
    Then status 409
    And print response

  Scenario: Test de creation de patient avec un mail déjà utilisée
    Given path '/patient'
    And request {"prenom": "pat", "nom": "ien", "email": "pat.ien@etu.univ-orleans.fr","numSecu": "124","numTel": "0123456789","dateNaissance": "14-02-2000","genre": "Homme"}
    When method post
    And match response == "Cette adresse mail est déjà utilisée pour un autre patient."
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
    And match response == "Ce patient n'existe pas !"
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
    And match response == "Ce médecin n'existe pas !"
    Then status 404
    And print response

  Scenario: Test d assigner un medecin à un patient inexistant
    Given path 'personnel/modif/patient/5487/medecintraitant'
    And request {"prenom": "med", "nom": "ecin"}
    When method patch
    And match response == "Ce patient n'existe pas !"
    Then status 404
    And print response

  Scenario: Test d assigner un medecin à un patient étant déjà assigné au medecin
    Given path 'personnel/modif/patient/154/medecintraitant'
    And request {"prenom": "med", "nom": "ecin"}
    When method patch
    And match response == "Ce patient est déjà assigné au médecin !"
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
    And match response == "Ce médecin n'existe pas !"
    Then status 404
    And print response

  Scenario: Test pour voir les consultations d'un medecin ayant une consultation inexistante
    Given path '/medecin/15/consultations'
    When method get
    And match response == "Le medecin a une consultation qui n'existe pas !"
    Then status 404
    And print response

  Scenario: Test pour voir les consultations d'un medecin n'ayant pas de consultation
    Given path '/medecin/2/consultations'
    When method get
    And match response == "Aucune consultation trouvée pour ce médecin !"
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
    And match response == "Ce patient n'existe pas !"
    Then status 404
    And print response

  Scenario: Test pour voir le medecin traitant d’un patient n’ayant pas de médecin traitant assigné
    Given path 'patient/888/medecintraitant'
    And match response == "Pas de médecin traitant assigné à ce patient"
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
    And match response == "Cette consultation n'existe pas !"
    When method get
    Then status 404
    And print response

  Scenario: Test pour voir les patients assignés à un médecin
    Given path 'medecin/12/patients'
    When method get
    Then status 200
    And print response

  Scenario: Test pour voir les patients assignés à un médecin inexistant
    Given path 'medecin/1/patients'
    When method get
    And match response == "Ce médecin n'existe pas !"
    Then status 404
    And print response

  Scenario: Test pour voir les patients assignés à un médecin n’ayant pas de patient assigné
    Given path 'medecin/14/patients'
    When method get
    And match response == "Ce medecin n'a pas de patient assigné !"
    Then status 404
    And print response

  Scenario: Test pour voir toutes les consultations
    Given path 'consultation/liste'
    When method get
    Then status 200
    And print response

  Scenario: Test pour voir toutes les consultations d’un type de consultation
    Given path 'consultation/liste/DENTAIRE'
    When method get
    Then status 200
    And print response

  Scenario: Test pour voir toutes les consultations de type de consultation n’existant pas
    Given path 'consultation/liste/ZZZ'
    When method get
    And match response == "Ce type de consultation n'existe pas !"
    Then status 406
    And print response
