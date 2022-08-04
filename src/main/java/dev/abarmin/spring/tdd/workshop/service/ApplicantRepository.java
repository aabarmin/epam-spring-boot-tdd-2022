package dev.abarmin.spring.tdd.workshop.service;

import dev.abarmin.spring.tdd.workshop.model.Applicant;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Aleksandr Barmin
 */
public interface ApplicantRepository {
  Applicant save(Applicant applicant);

  Optional<Applicant> getApplicantById(UUID applicantId);

  Optional<Applicant> getApplicantByEmail(String email);
}
