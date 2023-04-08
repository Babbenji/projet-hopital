Feature: Tests Integration du service gestion rdv patients et personnels
  Background:
    * url 'http://localhost:8083/api/v1/rdvpatients'
    * header Accept = 'application/json'

  Scenario: Test d'inscription d'une secretaire
    Given url 'http://localhost:8081/api/v1/auth/inscription'
    And request { "email": "fred22@hopital-secretaire.fr", "password": "fred" }
    When method post
    Then status 201
    And print response

  Scenario: Test connexion de la secretaire :
    Given url 'http://localhost:8081/api/v1/auth/connexion'
    And request { "email": "fred22@hopital-secretaire.fr", "password": "fred" }
    When method post
    Then status 200
    And print response
    And def token = response.token
    And print token
    And karate.set('monToken', token)

# Test de creation de medecin
    Given url 'http://localhost:8083/api/v1/rdvpatients/medecin'
    And header Authorization = 'Bearer ' + karate.get('monToken')
    And request {"prenom": "Mirabelle", "nom": "Soubai", "email": "mirabelle614.soubai@hopital-medecin.fr" }
    When method post
    Then status 201
    And print response

  # Test de creation de medecin déjà existant
    Given url 'http://localhost:8083/api/v1/rdvpatients/medecin'
    And header Authorization = 'Bearer ' + karate.get('monToken')
    And request {"prenom": "Mirabelle", "nom": "Soubai", "email": "mirabelle614.soubai@hopital-medecin.fr" }
    When method post
    Then status 409
    And print response

# Test de creation de patient
    Given url 'http://localhost:8083/api/v1/rdvpatients/patient'
    And header Authorization = 'Bearer ' + karate.get('monToken')
    And request {"prenom": "Joel", "nom": "Dino", "email": "joel1.dino@etu.univ-orleans.fr","numSecu": "155","numTel": "0123456789","dateNaissance": "14-02-2000","genre": "Homme"}
    When method post
    Then status 201
    And print response

#Test de creation de patient déjà existant
    Given url 'http://localhost:8083/api/v1/rdvpatients/patient'
    And header Authorization = 'Bearer ' + karate.get('monToken')
    And request  {"prenom": "Joel", "nom": "Dino", "email": "joel1.dino@etu.univ-orleans.fr","numSecu": "155","numTel": "0123456789","dateNaissance": "14-02-2000","genre": "Homme"}
    When method post
    Then status 409
    And print response

# Test pour voir les données d'un patient
    Given url 'http://localhost:8083/api/v1/rdvpatients/patient/155'
    And header Authorization = 'Bearer ' + karate.get('monToken')
    When method get
    Then status 200
    And print response

## Test pour voir les consultations d'un medecin
#    Given url 'http://localhost:8083/api/v1/rdvpatients/medecin/2/consultations'
#    And header Authorization = 'Bearer ' + karate.get('monToken')
#    When method get
#    Then status 200
#    And print response

# Test pour voir les consultations d'un medecin inexistant
    Given url 'http://localhost:8083/api/v1/rdvpatients/medecin/9/consultations'
    And header Authorization = 'Bearer ' + karate.get('monToken')
    When method get
    Then status 404
    And print response

    # Test pour voir les consultations d'un medecin n'ayant aucune consultation
    Given url 'http://localhost:8083/api/v1/rdvpatients/medecin/10/consultations'
    And header Authorization = 'Bearer ' + karate.get('monToken')
    When method get
    Then status 404
    And print response