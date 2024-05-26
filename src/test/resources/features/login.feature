Feature: Login

  Scenario: Successful login with valid credentials
    Given The user is on the login page
    When The user enters valid credentials
    Then The user should be redirected to next page
