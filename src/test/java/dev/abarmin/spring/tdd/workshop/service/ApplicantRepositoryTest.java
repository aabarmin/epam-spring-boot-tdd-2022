package dev.abarmin.spring.tdd.workshop.service;

import dev.abarmin.spring.tdd.workshop.model.Applicant;
import dev.abarmin.spring.tdd.workshop.model.ContactPoint;
import dev.abarmin.spring.tdd.workshop.model.ElectronicAddress;
import dev.abarmin.spring.tdd.workshop.model.Person;
import dev.abarmin.spring.tdd.workshop.model.PersonName;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aleksandr Barmin
 */
@DataJpaTest
class ApplicantRepositoryTest {
  @Autowired
  private ApplicantRepository applicantRepository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  void checkContextStarts() {
    assertThat(applicantRepository)
        .isNotNull();
  }

  @Test
  void findByEmail_shouldFindSavedApplicant() {
    final Applicant applicant = Applicant.builder()
        .person(Person.builder()
            .personName(PersonName.builder()
                .firstName("Aleks")
                .build())
            .build())
        .contactPoint(ContactPoint.builder()
            .electronicAddress(ElectronicAddress.builder()
                .email("aleks@test.com")
                .build())
            .build())
        .build();

    entityManager.persist(applicant);

    final Optional<Applicant> byEmail = applicantRepository.getApplicantByEmail("aleks@test.com");

    assertThat(byEmail)
        .isNotNull()
        .isPresent()
        .withFailMessage("Were not extracted from db");
  }
}