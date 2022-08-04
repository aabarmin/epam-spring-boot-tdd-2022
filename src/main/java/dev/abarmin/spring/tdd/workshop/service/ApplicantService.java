package dev.abarmin.spring.tdd.workshop.service;

import dev.abarmin.spring.tdd.workshop.model.Applicant;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author Aleksandr Barmin
 */
@Service
@Validated
@RequiredArgsConstructor
public class ApplicantService {
  private final ApplicantRepository applicantRepository;

  public Applicant save(@Valid @NotNull final Applicant applicant) {
    return applicantRepository.save(applicant);
  }

  public Optional<Applicant> getApplicant(@NotNull final UUID applicantId) {
    return applicantRepository.findById(applicantId);
  }

  public Optional<Applicant> getApplicant(@NotNull @Email final String applicantEmail) {
    return applicantRepository.getApplicantByEmail(applicantEmail);
  }
}
