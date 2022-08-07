Feature: the applicant can be created
  Scenario: client makes an empty request to POST /applicants
    Given an empty request body
    When a client makes a POST request to /applicants
    Then response code is 400

  Scenario: client makes a valid request to POST /applicants
    Given a request with body
      | firstName | First Name |
      | lastName  | Last Name  |
      | middleName | Middle Name |
      | email      | test@email.com |
    When a client makes a POST request to /applicants
    Then response code is 201