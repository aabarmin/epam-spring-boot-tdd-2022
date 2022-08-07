Feature: the applicant can be created
  Scenario: client makes an empty request to POST /applicants
    Given an empty request body
    When a client makes a POST request to /applicants
    Then response code is 400
