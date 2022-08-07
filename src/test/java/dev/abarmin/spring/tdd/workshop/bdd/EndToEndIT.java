package dev.abarmin.spring.tdd.workshop.bdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.spring.tdd.workshop.model.Applicant;
import dev.abarmin.spring.tdd.workshop.model.ContactPoint;
import dev.abarmin.spring.tdd.workshop.model.ElectronicAddress;
import dev.abarmin.spring.tdd.workshop.model.Person;
import dev.abarmin.spring.tdd.workshop.model.PersonName;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Aleksandr Barmin
 */
@Testable
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("dev/abarmin/spring/tdd/workshop/bdd/bdd.feature")
public class EndToEndIT {
  private String request;
  private ResponseEntity<Object> response;

  private ObjectMapper objectMapper = new ObjectMapper();

  private RestTemplate restTemplate = new RestTemplateBuilder().build();
  private String baseUrl = "http://localhost:8080";

  @Given("^an empty request body$")
  public void emptyRequestBody() {
    request = "";
  }

  @Given("^a request with body$")
  public void requestWithBody(final Map<String, String> values) throws Exception {
    final Applicant applicant = Applicant.builder()
        .person(Person.builder()
            .personName(PersonName.builder()
                .build())
            .build())
        .contactPoint(ContactPoint.builder()
            .electronicAddress(ElectronicAddress.builder()
                .build())
            .build())
        .build();

    for (Map.Entry<String, String> entry : values.entrySet()) {
      if (StringUtils.equalsIgnoreCase(entry.getKey(), "firstName")) {
        applicant.getPerson().getPersonName().setFirstName(entry.getValue());
      } else if (StringUtils.equalsIgnoreCase(entry.getKey(), "lastName")) {
        applicant.getPerson().getPersonName().setLastName(entry.getValue());
      } else if (StringUtils.equalsIgnoreCase(entry.getKey(), "middleName")) {
        applicant.getPerson().getPersonName().setMiddleName(entry.getValue());
      } else if (StringUtils.equalsIgnoreCase(entry.getKey(), "email")) {
        applicant.getContactPoint().getElectronicAddress().setEmail(entry.getValue());
      } else {
        throw new UnsupportedOperationException("Unsupported key " + entry.getKey());
      }
    }

    this.request = objectMapper.writeValueAsString(applicant);
  }

  @When("^a client makes a (.+) request to (.+)$")
  public void sendRequest(final HttpMethod method, final String endpoint) {
    final HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.APPLICATION_JSON);

    final HttpEntity<Object> requestEntity = new HttpEntity<>(
        request,
        requestHeaders
    );

    try {
      this.response = this.restTemplate.exchange(
          this.baseUrl + endpoint,
          method,
          requestEntity,
          Object.class
      );
    } catch (HttpClientErrorException e) {
      this.response = new ResponseEntity<>(e.getStatusCode());
    }
  }

  @Then("^response code is (\\d+)$")
  public void checkResponseStatus(final int responseCode) {
    assertThat(response)
        .isNotNull()
        .withFailMessage("Response is null");

    assertThat(response.getStatusCodeValue())
        .isEqualTo(responseCode)
        .withFailMessage("Invalid response code");
  }
}
