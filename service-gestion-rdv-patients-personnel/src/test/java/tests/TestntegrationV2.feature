Feature: Tests Integration du service gestion rdv patients et personnels
Background:
  * url 'http://localhost:8083/api/v1/rdvpatients'
  * header Accept = 'application/json'

  Scenario: Test d'inscription d'une secretaire
    Given url 'http://localhost:8081/api/v1/auth/inscription'
    And request { "email": "fred8@hopital-secretaire.fr", "password": "fred" }
    When method post
    Then status 201
    And print response
    And def token = response.token
    And def oauthToken = `Bearer ${response.token}`
    And print token
    And karate.set('monToken', token)
    And print karate.get('monToken')

    Given path '/medecin'
    And print karate.get('monToken')

#    And header Authorization = 'Bearer ' + karate.get('monToken')
    And header Authorization = oauthToken
    And request {"prenom": "Mirabelle", "nom": "Soubai", "email": "mirabelle31.soubai@hopital-medecin.fr" }
    When method post
    Then status 201
    And print response

