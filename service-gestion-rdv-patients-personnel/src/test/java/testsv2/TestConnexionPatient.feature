Feature: Tests Connexion d'un patient
  Background:
    * url 'http://localhost:8081/api/v1/auth/connexion'
    * header Accept = 'application/json'

  Scenario: Connexion avec mauvais mdp
    And request { "email": "userpat@outlook.fr", "password": "mdp123" }
    When method post
    Then status 403
    And print response

  Scenario: Connexion avec mauvais login
    And request { "email": "pat@outlook.fr", "password": "pwd123" }
    When method post
    Then status 403
    And print response

  Scenario: Connexion
    And request { "email": "userpat@outlook.fr", "password": "pwd123" }
    When method post
    Then status 200
    And print response
    And def token = response.token
    And print token