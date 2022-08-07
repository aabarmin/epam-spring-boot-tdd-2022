package dev.abarmin.spring.tdd.workshop.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.platform.engine.Cucumber;
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
@Cucumber
public class EndToEndTest {
  private String request;
  private ResponseEntity<Object> response;

  private RestTemplate restTemplate = new RestTemplateBuilder().build();
  private String baseUrl = "http://localhost:8080";

  @Given("^an empty request body$")
  public void emptyRequestBody() {
    request = "";
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
