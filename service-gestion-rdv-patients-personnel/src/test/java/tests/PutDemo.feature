Feature: PUT API DEMO
  Scenario: Put demo 1
    Given url 'https://reqres.in/api/users/2'
    And request {"name":"hind", "job":"tkt"}
    When method put
    Then status 200
    And print response
    And print responseStatus

    #Le reste comme le post