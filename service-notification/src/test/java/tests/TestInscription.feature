Feature: Tests l'inscription d'un secretaire, d'un medecin, d'un patient
  Scenario: Inscription admin
    Given url 'http://localhost:8081/api/v1/auth/inscription'
    And request { "email": "admin@hopital.fr", "password": "pwd123" }
    When method post
    Then status 201
    And print response

  Scenario: Inscription admin déja inscrit
    Given url 'http://localhost:8081/api/v1/auth/inscription'
    And request { "email": "admin@hopital.fr", "password": "pwd123" }
    When method post
    Then status 409
    And print response

