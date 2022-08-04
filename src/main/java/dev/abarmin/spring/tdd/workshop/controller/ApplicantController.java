package dev.abarmin.spring.tdd.workshop.controller;

import dev.abarmin.spring.tdd.workshop.model.Applicant;
import dev.abarmin.spring.tdd.workshop.service.ApplicantService;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aleksandr Barmin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/applicants")
public class ApplicantController {
  private final ApplicantService applicantService;

  @PostMapping
  public ResponseEntity<Void> createApplicant(@RequestBody Applicant applicant) {
    final Applicant savedApplicant = applicantService.save(applicant);

    return ResponseEntity.created(URI.create("/applicants/")
            .resolve(savedApplicant.getApplicantId().toString()))
        .build();
  }

  @GetMapping("/{applicantId}")
  public ResponseEntity<Applicant> getApplicant(final @PathVariable("applicantId") UUID applicantId) {
    return applicantService.getApplicant(applicantId)
        .map(applicant ->  ResponseEntity.ok(applicant))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
