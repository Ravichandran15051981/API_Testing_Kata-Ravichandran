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

  @booking @negative @validation @create
  Scenario Outline: Create booking with invalid or missing fields returns validation error
    Given I have invalid create booking payload with roomid "<roomid>", bookingid "<bookingid>", firstname "<firstname>", lastname "<lastname>", depositpaid "<depositpaid>", email "<email>", phone "<phone>", checkin "<checkin>", checkout "<checkout>", unexpectedField "<unexpectedField>"
    When I send a POST request to create booking with invalid payload
    Then the response status code should be <statusCode>
    And the response body should contain "<expectedMessage>"

    Examples:
      | roomid | bookingid | firstname                               | lastname                                           | depositpaid | email                | phone       | checkin    | checkout   | unexpectedField | statusCode | expectedMessage                     |
      | 1      | OMIT      |                                         | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | OMIT            | 400        | size must be between 3 and 18       |
      | 1      | OMIT      | TestFName                               |                                                    | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | OMIT            | 400        | size must be between 3 and 30       |
      | 1      | OMIT      | NULL                                    | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | OMIT            | 400        | Firstname should not be blank       |
      | 1      | OMIT      | TestFName                               | NULL                                               | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | OMIT            | 400        | Lastname should not be blank        |
      | 1      | OMIT      | ab                                      | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | OMIT            | 400        | size must be between 3 and 18       |
      | 1      | OMIT      | aVeryLongFirstNameThatExceedsMaximumLen | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | OMIT            | 400        | size must be between 3 and 18       |
      | 1      | OMIT      | TestFName                               | ab                                                 | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | OMIT            | 400        | size must be between 3 and 30       |
      | 1      | OMIT      | TestFName                               | aVeryLongLastNameThatWillExceedThirtyCharactersXYZ | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | OMIT            | 400        | size must be between 3 and 30       |
      | 1      | OMIT      | TestFName                               | TestLName                                          | yes         | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | OMIT            | 400        | Failed to create booking            |
      | 1      | OMIT      | TestFName                               | TestLName                                          | 123         | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | OMIT            | 400        | Failed to create booking            |
      | 1      | OMIT      | TestFName                               | TestLName                                          | true        | not-an-email         | 99999999999 | 2027-09-27 | 2027-09-30 | OMIT            | 400        | must be a well-formed email address |
      | 1      | OMIT      | TestFName                               | TestLName                                          | true        | test@.com            | 99999999999 | 2027-09-27 | 2027-09-30 | OMIT            | 400        | must be a well-formed email address |
      | 1      | OMIT      | TestFName                               | TestLName                                          | true        | christopher@test.com | 99999999999 | 27-09-2027 | 30-09-2027 | OMIT            | 400        | Failed to create booking            |
      | 1      | OMIT      | TestFName                               | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027/09/27 | 2027/09/30 | OMIT            | 400        | Failed to create booking            |
      | 1      | OMIT      | TestFName                               | TestLName                                          | true        | christopher@test.com | 99999999999 | 0000-00-00 | 0000-00-00 | OMIT            | 400        | Failed to create booking            |
      | OMIT   | OMIT      | TestFName                               | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | OMIT            | 400        | must be greater than or equal to 1  |
      | 0      | OMIT      | TestFName                               | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | OMIT            | 400        | must be greater than or equal to 1  |
      | -1     | OMIT      | TestFName                               | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | OMIT            | 400        | must be greater than or equal to 1  |
      | 1      | OMIT      | TestFName                               | TestLName                                          | true        | christopher@test.com | 99999999999 | OMIT       | 2027-09-30 | OMIT            | 400        | must not be null                    |
      | 1      | OMIT      | TestFName                               | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027-09-27 | OMIT       | OMIT            | 400        | must not be null                    |
      | 1      | OMIT      | TestFName                               | TestLName                                          | true        | christopher@test.com | 9999999999  | 2027-09-27 | 2027-09-30 | OMIT            | 400        | size must be between 11 and 21      |

  @booking @negative @get
  Scenario Outline: Fetch booking with invalid booking id returns error
    Given I use invalid booking id "<bookingId>"
    When I send a GET request using invalid booking id
    Then the response status code should be <statusCode>

    Examples:
      | bookingId | statusCode |
      | 99999999  | 404        |
      | 0         | 404        |
      | -1        | 404        |

  @booking @negative @update
  Scenario Outline: Update booking with invalid booking id returns error
    Given I have a valid auth token
    And I have a valid update booking payload
    And I use invalid booking id "<bookingId>"
    When I send a PUT request using invalid booking id
    Then the response status code should be <statusCode>

    Examples:
      | bookingId | statusCode |
      | 99999999  | 404        |
      | 0         | 404        |
      | -1        | 404        |

  @booking @negative @validation @update
  Scenario Outline: Update booking with invalid or missing fields returns validation error
    Given I have created a booking
    And I have a valid auth token
    And I have invalid update booking payload with roomid "<roomid>", firstname "<firstname>", lastname "<lastname>", depositpaid "<depositpaid>", email "<email>", phone "<phone>", checkin "<checkin>", checkout "<checkout>"
    When I send a PUT request to update booking with invalid payload
    Then the response status code should be <statusCode>
    And the response body should contain "<expectedMessage>"

    Examples:
      | roomid | firstname                               | lastname                                           | depositpaid | email                | phone       | checkin    | checkout   | statusCode | expectedMessage                     |
      | 1      |                                         | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | 400        | size must be between 3 and 18       |
      | 1      | TestFName                               |                                                    | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | 400        | size must be between 3 and 30       |
      | 1      | NULL                                    | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | 400        | Firstname should not be blank       |
      | 1      | TestFName                               | NULL                                               | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | 400        | Lastname should not be blank        |
      | 1      | ab                                      | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | 400        | size must be between 3 and 18       |
      | 1      | aVeryLongFirstNameThatExceedsMaximumLen | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | 400        | size must be between 3 and 18       |
      | 1      | TestFName                               | ab                                                 | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | 400        | size must be between 3 and 30       |
      | 1      | TestFName                               | aVeryLongLastNameThatWillExceedThirtyCharactersXYZ | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | 400        | size must be between 3 and 30       |
      | 1      | TestFName                               | TestLName                                          | yes         | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | 400        | Bad Request                         |
      | 1      | TestFName                               | TestLName                                          | 123         | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | 409        |                                     |
      | 1      | TestFName                               | TestLName                                          | true        | not-an-email         | 99999999999 | 2027-09-27 | 2027-09-30 | 400        | must be a well-formed email address |
      | 1      | TestFName                               | TestLName                                          | true        | christopher@test.com | 9999999999  | 2027-09-27 | 2027-09-30 | 400        | size must be between 11 and 21      |
      | 0      | TestFName                               | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | 400        | must be greater than or equal to 1  |
      | -1     | TestFName                               | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027-09-27 | 2027-09-30 | 400        | must be greater than or equal to 1  |
      | 1      | TestFName                               | TestLName                                          | true        | christopher@test.com | 99999999999 | OMIT       | 2027-09-30 | 400        | must not be null                    |
      | 1      | TestFName                               | TestLName                                          | true        | christopher@test.com | 99999999999 | 2027-09-27 | OMIT       | 400        | must not be null                    |
