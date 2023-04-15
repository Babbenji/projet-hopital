Feature: Tests Integration du service gestion rdv patients et personnels
  Background:
    * url 'http://localhost:8083/api/v1/rdvpatients'
    * header Accept = 'application/json'

#  Scenario: Test d'inscription d'un medecin
#    Given url 'http://localhost:8081/api/v1/auth/inscription'
#    And request { "email": "medecin@hopital-medecin.fr", "password": "pswd" }
#    When method post
#    Then status 201
#    And print response
#
  Scenario: Test connexion d'un medecin :
    Given url 'http://localhost:8081/api/v1/auth/connexion'
    And request { "email": "medecin@hopital-medecin.fr", "password": "pswd" }
    When method post
    Then status 200
    And print response
    And def token = response.token
    And print token
    And karate.set('monToken', token)

#  #Test d'ajout des antécédents d'un patient à son profil
#    Given url 'http://localhost:8083/api/v1/rdvpatients/personnel/modif/patient/155/antecedents'
#    And header Authorization = 'Bearer ' + karate.get('monToken')
#    And request {"antecedents": "Allergique"}
#    When method patch
#    Then status 200
#    And print response
#
## Test pour voir les données d'un patient
#    Given url 'http://localhost:8083/api/v1/rdvpatients/patient/154'
#    And header Authorization = 'Bearer ' + karate.get('monToken')
#    When method get
#    Then status 200
#    And print response

## Test de confirmation de la consultation par le medecin
#    Given url 'http://localhost:8083/api/v1/rdvpatients/consultation/1/confirmer'
#    And header Authorization = 'Bearer ' + karate.get('monToken')
#    When method patch
#    Then status 200
#    And print response

## Test de confirmation de la consultation déjà confirmé par le medecin
#    Given url 'http://localhost:8083/api/v1/rdvpatients/consultation/1/confirmer'
#    And header Authorization = 'Bearer ' + karate.get('monToken')
#    When method patch
#    Then status 409
#    And match response == 'Cette consultation est déjà confirmée !'
#    And print response
#
#  #Test pour voir les consultations d'un medecin n'ayant pas de consultation
#    Given url 'http://localhost:8083/api/v1/rdvpatients/medecin/1/consultations'
#    And header Authorization = 'Bearer ' + karate.get('monToken')
#    When method get
#    Then status 404
#    And print response

## Test d'ajout du compte rendu dans la consultation
#    Given url 'http://localhost:8083/api/v1/rdvpatients/consultation/1/compterendu'
#    And header Authorization = 'Bearer ' + karate.get('monToken')
#    And request {"compteRendu":"Patient enrhumé"}
#    When method patch
#    Then status 200
#    And match response == 'Le compte rendu pour la consultation n°1 :\n est : Patient enrhumé'
#    And print response

##Test pour voir les consultations d'un medecin
#    Given url 'http://localhost:8083/api/v1/rdvpatients/medecin/2/consultations'
#    And header Authorization = 'Bearer ' + karate.get('monToken')
#    When method get
#    Then status 200
#    And print response
##
##Test pour voir les consultations d'un medecin inexistant
#    Given url 'http://localhost:8083/api/v1/rdvpatients/medecin/9/consultations'
#    And header Authorization = 'Bearer ' + karate.get('monToken')
#    When method get
#    Then status 404
#    And print response
#

