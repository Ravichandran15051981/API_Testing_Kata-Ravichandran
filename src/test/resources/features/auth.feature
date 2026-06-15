Feature: Authentication API

  @auth
  Scenario: Generate auth token using valid credentials
    Given I have valid authentication credentials
    When I send a POST request to generate auth token
    Then the response should contain auth token