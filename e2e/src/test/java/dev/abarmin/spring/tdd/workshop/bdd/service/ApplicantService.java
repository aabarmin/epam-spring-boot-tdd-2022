package dev.abarmin.spring.tdd.workshop.bdd.service;

import dev.abarmin.spring.tdd.workshop.bdd.config.BaseUrl;
import dev.abarmin.spring.tdd.workshop.model.Applicant;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Aleksandr Barmin
 */
@Service
public class ApplicantService {
  @Autowired
  private RestTemplate restTemplate;

  @BaseUrl
  @Autowired
  private String baseUrl;

  public ResponseEntity<Void> createAccount(final Applicant applicant) {
    try {
      return restTemplate.postForEntity(baseUrl + "/applicants", applicant, Void.class);
    } catch (HttpClientErrorException.Conflict e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

  public Optional<Applicant> findApplicantByEmail(final String email) {
    final var url = baseUrl + "/applicants?email=" + email;
    try {
      final Applicant applicant = restTemplate.getForObject(url, Applicant.class);
      return Optional.of(applicant);
    } catch (HttpClientErrorException.NotFound e) {
      return Optional.empty();
    }
  }

  public void deleteApplicant(final Long applicantId) {
    final var url = baseUrl + "/applicants/" + applicantId;
    restTemplate.delete(url);
  }
}
