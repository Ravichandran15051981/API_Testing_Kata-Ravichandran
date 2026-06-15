Feature: Booking management API

  @booking @create
  Scenario: Create a new booking
    Given I have a valid booking payload
    When I send a POST request to create booking
    Then the response status code should be 200 or 201
    And the response should contain booking id
    And the booking details should be correct

  @booking @get
  Scenario: Fetch booking by dynamically created booking id
    Given I have created a booking
    When I send a GET request using the booking id
    Then the response status code should be 200
    And the booking details should be correct

  @booking @update
  Scenario: Update booking by dynamically created booking id
    Given I have created a booking
    And I have a valid auth token
    And I have a valid update booking payload
    When I send a PUT request to update the booking
    Then the response status code should be 200
    And the updated booking details should be correct

  @booking @patch
  Scenario: Partially update booking by dynamically created booking id
    Given I have created a booking
    And I have a valid auth token
    And I have a valid partial update booking payload
    When I send a PATCH request to partially update the booking
    Then the response status code should be 200
    And the partially updated booking details should be correct

  @booking @delete
  Scenario: Delete booking by dynamically created booking id
    Given I have created a booking
    And I have a valid auth token
    When I send a DELETE request using the booking id
    Then the response status code should be 200 or 202
    And the booking should not be available