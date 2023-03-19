Feature: Sample API Tests
  Background:
    * url 'https://reqres.in/api'
    * header Accept = 'application/json'

    #Simple Get Request
  Scenario: Test a Sample Get API 1
    Given url "https://reqres.in/api/users?page=2"
    When method GET
    Then status 200
    And print response
    And print responseStatus
    And print responseHeaders


#Get with Background and path
  Scenario: Test a Sample Get API 2
    Given path '/users?page=2'
    When method GET
    Then status 200
    And print response
    And print responseStatus
    And print responseHeaders



    #Get with Background, path and params
  Scenario: Get 3
    Given path '/users'
    And param page = 2
    When method GET
    Then status 200
    And print response


    #Assertions
  Scenario: Get 4
    Given path '/users'
    And param page = 2
    When method GET
    Then status 200
    And print response
    And match response.data[0].first_name != null
    And assert response.data.length == 6
    And match $.data[3].id == 10
    And match response.data[4].last_name == 'Edwards'
