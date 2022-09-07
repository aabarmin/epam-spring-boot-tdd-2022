package dev.abarmin.spring.tdd.workshop.bdd.stepdefs;

import dev.abarmin.spring.tdd.workshop.bdd.service.ApplicantService;
import dev.abarmin.spring.tdd.workshop.model.Applicant;
import dev.abarmin.spring.tdd.workshop.model.ContactPoint;
import dev.abarmin.spring.tdd.workshop.model.ElectronicAddress;
import dev.abarmin.spring.tdd.workshop.model.Person;
import dev.abarmin.spring.tdd.workshop.model.PersonName;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Aleksandr Barmin
 */
public class CreateAccountStepDefinitions {
  private Applicant.ApplicantBuilder builder;
  private ResponseEntity<?> lastResponse;

  @Autowired
  private ApplicantService applicantService;

  @Before
  public void createBuilder() {
    builder = Applicant.builder();
  }

  @Given("^an applicant with email ([a-z@\\.]+) is new to bank$")
  public void anApplicantIsNewToBank(final String email) {
    applicantService.findApplicantByEmail(email)
        .map(Applicant::getApplicantId)
        .ifPresent(applicantService::deleteApplicant);
  }

  @Given("^an applicant with email ([a-z@\\.]+) known to bank$")
  public void anApplicantIsKnownToBank(final String email) {
    anApplicantIsNewToBank(email);
    whenApplicantProvidesAValidEmail(email);
    andApplicantProvidesAValidLastName();
    andApplicantCreatesAnAccount();
  }

  @When("^an applicant provides ([a-z@\\.]+) email$")
  public void whenApplicantProvidesAValidEmail(final String email) {
    builder.contactPoint(ContactPoint.builder()
        .electronicAddress(ElectronicAddress.builder()
            .email(email)
            .build())
        .build());
  }

  @And("an applicant provides a valid last name")
  public void andApplicantProvidesAValidLastName() {
    builder.person(Person.builder()
        .personName(PersonName.builder()
            .lastName("Valid")
            .build())
        .build());
  }

  @And("an applicant creates an account")
  public void andApplicantCreatesAnAccount() {
    lastResponse = applicantService.createAccount(builder.build());
  }

  @Then("then app should return ID of a created record")
  public void thenAppShouldProvideIdOfCreatedRecord() {
    assertThat(lastResponse.getHeaders())
        .hasEntrySatisfying(HttpHeaders.LOCATION, value -> {
          assertThat(value)
              .isNotEmpty()
              .hasSize(1);
        });
  }

  @And("an applicant wants to get information about account")
  public void andApplicantWantsToGetInformationAboutAccount() {
    final Applicant applicant = builder.build();
    lastResponse =
        applicantService.findApplicantByEmail(applicant.getContactPoint().getElectronicAddress().getEmail())
            .map(found -> ResponseEntity.ok(found))
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Then("the app should return the record")
  public void theAppShouldReturnTheRecord() {
    assertThat(lastResponse)
        .withFailMessage("Last response is null")
        .isNotNull();

    assertThat(lastResponse.getStatusCode())
        .isEqualTo(HttpStatus.OK);
  }

  @And("^response should contain email ([a-z@\\.]+)$")
  public void responseShouldContainEmail(final String email) {
    final Applicant applicant = Applicant.class.cast(lastResponse.getBody());
    final String retrievedEmail = applicant.getContactPoint().getElectronicAddress().getEmail();

    assertThat(retrievedEmail)
        .withFailMessage("Invalid retrieved email")
        .isEqualTo(email);
  }

  @Then("the app should return {int} response code")
  public void thenAppShouldReturnStatusCode(final int statusCode) {
    assertThat(lastResponse.getStatusCode().value())
        .isEqualTo(statusCode);
  }
}
