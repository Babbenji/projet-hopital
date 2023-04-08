Feature: Tests Integration du service gestion rdv patients et personnels
  Background:
    * url 'http://localhost:8083/api/v1/rdvpatients'
    * header Accept = 'application/json'
    * def signIn = call read('marchestp1.feature')
    * configure headers = { Authorization: '#(signIn.token)' },

    Scenario: Test creation d'un medecin
      Given path '/medecin'
      And request {"prenom": "Mirabelle", "nom": "Soubai", "email": "mirabelle31.soubai@hopital-medecin.fr" }
      When method post
      Then status 201
      And print response



