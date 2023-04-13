Feature: Tests l'inscription d'un secretaire, d'un medecin, d'un patient
  Scenario: Inscription Secretaire
    Given url 'http://localhost:8081/api/v1/auth/inscription'
    And request { "email": "usersecr@hopital-secretaire.fr", "password": "pwd123" }
    When method post
    Then status 201
    And print response

  Scenario: Inscription Secretaire déja inscrit
    Given url 'http://localhost:8081/api/v1/auth/inscription'
    And request { "email": "usersecr@hopital-secretaire.fr", "password": "pwd123" }
    When method post
    Then status 409
    And print response

