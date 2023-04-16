Feature: Tests Integration email et notifciation
  Background:
    * url 'http://localhost:8082/api/v1/notif'
    * header Accept = 'application/json'
    * def signIn = call read('TestConnexionAdmin.feature')
    * configure headers = { Authorization: '#(signIn.token)' },


  Scenario: Test get all emails from database
    Given path '/emails'
    When method GET
    Then status 200
    And print response


  Scenario: Test send email
    Given path '/send-email'
    And request {"destinataire": "rachida.el-ouariachi@etu.univ-orleans.fr","objet": "Test du service de messagerie du micro-service Hopital (M2 MIAGE)","contenu": "Bonjour, ceci est un e-mail de test pour vérifier que notre service de messagerie fonctionne correctement.\n\nCordialement, \n\nDes étudiants en M2 MIAGE de l'Université d'Orléans."}
    When method POST
    Then status 201
    And print response

  Scenario: Test send email with wrong email
    Given path '/send-email'
    And request {"destinataire": "blablouuu","objet": "Test du service de messagerie du micro-service Hopital (M2 MIAGE)","contenu": "Bonjour, ceci est un e-mail de test pour vérifier que notre service de messagerie fonctionne correctement.\n\nCordialement, \n\nDes étudiants en M2 MIAGE de l'Université d'Orléans."}
    When method POST
    Then status 400
    And print response

