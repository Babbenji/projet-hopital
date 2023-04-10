Feature: Tests Integration du service gestion rdv patients et personnels

  Scenario: Connexion avec mauvais mdp
    Given url 'http://localhost:8081/api/v1/auth/connexion'
    And request { "email": "usersecr@hopital-secretaire.fr", "password": "mdp123" }
    When method post
    Then status 403
    And print response

  Scenario: Connexion avec mauvais login
    Given url 'http://localhost:8081/api/v1/auth/connexion'
    And request { "email": "secret@hopital-secretaire.fr", "password": "pwd123" }
    When method post
    Then status 403
    And print response

  Scenario: Connexion
    Given url 'http://localhost:8081/api/v1/auth/connexion'
    And request { "email": "usersecr@hopital-secretaire.fr", "password": "pwd123" }
    When method post
    Then status 200
    And print response
    And def token = response.token
    And print token