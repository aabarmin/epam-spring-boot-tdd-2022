Feature: the applicant can be created
  Scenario: Create a bank account for new to bank applicant
    Given an applicant with email test@test.com is new to bank
    When an applicant provides test@test.com email
    And an applicant provides a valid last name
    And an applicant creates an account
    Then then app should return ID of a created record

  Scenario: Create a bank account for known customer
    Given an applicant with email test@test.com known to bank
    When an applicant provides test@test.com email
    And an applicant provides a valid last name
    And an applicant creates an account
    Then the app should return 409 response code

  Scenario: Get known applicant by email
    Given an applicant with email test@test.com known to bank
    When an applicant provides test@test.com email
    And an applicant wants to get information about account
    Then the app should return the record
    And response should contain email test@test.com

  Scenario: Get unknown applicant by email
    Given an applicant with email test@test.com is new to bank
    When an applicant provides test@test.com email
    And an applicant wants to get information about account
    Then the app should return 404 response code