Feature: Post API Demo
  Background:
    * url 'https://reqres.in/api'
    * header Accept = 'application/json'

    #Simple Post request
  Scenario: Post Demo 1
    Given url 'https://reqres.in/api/users'
  #Request Body
    And request {"name":"Raghav", "job":"leader"}
    When method post
    Then status 201
    And print response

   # post scenario with background
  Scenario: Post Demo 2
    Given path '/users'
    And request {"name": "Raghav", "job": "leader"}
    When method post
    Then status 201
    And print response

        #post scenario with assertions
  Scenario: Post Demo 3
    Given path '/users'
    And request {"name":"Raghav", "job":"teacher"}
    When method post
    Then status 201
    And match response == { "createdAt":"#ignore", "name":"Raghav","id":"#string", "job":"teacher"}
    And print response

  Scenario: Post Demo 4
    Given path '/users'
    And def requestBody = read("request1.json")
    And request requestBody
    When method post
    Then status 201
    And match response == { "createdAt":"#ignore", "name":"Raghav","id":"#string", "job":"teacher"}
    And print response

    #modifier une valeur du fichier body qu'on lit pour creer
  Scenario: Post Demo 5
    Given path '/users'
    And def requestBody = read("request1.json")
    And request requestBody
    And set requestBody.job = 'teacher'
    When method post
    Then status 201
    And match response == { "createdAt":"#ignore", "name":"Raghav","id":"#string", "job":"teacher"}
    And print response