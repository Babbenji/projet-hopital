Feature: Tests Integration du service gestion rdv patients et personnels
  Scenario: Test d'inscription d'une secretaire
    Given url 'http://localhost:8081/api/v1/auth/inscription'
    And request { "email": "fred74@hopital-secretaire.fr", "password": "fred" }
    When method post
    Then status 201
    And print response

  Scenario: Test connexion de la secretaire :
    Given url 'http://localhost:8081/api/v1/auth/connexion'
    And request { "email": "fred74@hopital-secretaire.fr", "password": "fred" }
    When method post
    Then status 200
    And print response
    And def token = response.token
    And print token

