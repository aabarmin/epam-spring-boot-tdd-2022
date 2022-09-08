# TDD with Spring Boot and Cucumber

This repository stores examples for my talk about Test-Driven Development with Spring Boot and Cucumber. As you may notice, there are two Apache Maven modules:

- `app` - the application itself.
- `e2e` - acceptance tests written with Cucumber.

## Application

The application is from banking business domain - it demonstrates main operations which may be required during application of new to bank clients. The application has REST interface which publishes the following operations:

- `POST /applicants` - create a new applicant.
- `GET /applicants/{applicantId}` - get an applicant by `applicantId`.
- `GET /applicants?email={email}` - get an applicant by `email`.
- `DELETE /applicants/{applicantId}` - delete an existing applicant.

All the information about applicants is stored in the relational database, REST endpoints aren't protected by Spring Security or any other security framework.

The application has pretty standard layered architecture: REST endpoints, next business layer and data access layer. There are tests for every component.

The application is built using Apache Maven, main result of the build is a Docker image created by Spring Boot Maven plugin.

The application exposes `/actuator/health` endpoint to check the application state.

## Acceptance tests

Acceptance tests are stored in a separate Apache Maven module with name `e2e`. Actually, there is only one test class and one single feature-file for Cucumber.

Tests are executed automatically and use Apache Maven Failsafe plugin. The reason to use this plugin is that it is executed during `integration-test` phase so that it's possible to run Docker container with the application during `pre-integration-test` phase and stop it afterwards during the `after-integration-test` phase.
