Feature: Tests Integration
  Background:
    * url 'http://localhost:8080/rdvpatients'
    * header Accept = 'application/json'

  Scenario: Test de creation de medecin
    Given path '/medecin'
    And request {"prenom": "Mirabelle", "nom": "Soubai", "email": "mirabelle.soubai@etu.univ-orleans.fr" }
    When method post
    Then status 201
    And match response == { "id": "#number", "prenom": "Mirabelle", "nom": "Soubai", "email": "mirabelle.soubai@etu.univ-orleans.fr","listeConsultations": [],"listePatients": []}
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
    And request {"prenom": "Joel", "nom": "Dino", "email": "joel.dino@etu.univ-orleans.fr","numSecu": "15","numTel": "0123456789","dateNaissance": "14-02-2000","genre": "Homme"}
    When method post
    Then status 201
    And match response == { "id": "#number", "prenom": "Joel", "nom": "Dino", "email": "joel.dino@etu.univ-orleans.fr","numSecu": "15","numTel": "0123456789","dateNaissance": "14-02-2000","genre": "Homme","idMedecinTraitant":0,"antecedents":null}
    And print response

  Scenario: Test de creation de patient déjà existant
    Given path '/patient'
    And request  {"prenom": "Joel", "nom": "Dino", "email": "joel.dino@etu.univ-orleans.fr","numSecu": "15","numTel": "0123456789","dateNaissance": "14-02-2000","genre": "Homme"}
    When method post
    Then status 409
    And match response == 'Ce numéro de sécurité sociale est déjà attribué à un autre patient.'
    And print response


