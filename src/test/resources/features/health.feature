Feature: Booking API health check

  @health
  Scenario: Verify booking service health status
    Given the booking API is available
    When I send a GET request to the health endpoint
    Then the response status code should be 200
    And the response body should not be empty
    And the health response should contain status "UP"