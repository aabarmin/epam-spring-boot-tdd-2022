package dev.abarmin.spring.tdd.workshop.controller;

import dev.abarmin.spring.tdd.workshop.model.Applicant;
import dev.abarmin.spring.tdd.workshop.service.ApplicantExistsException;
import dev.abarmin.spring.tdd.workshop.service.ApplicantService;
import java.net.URI;
import java.util.Collection;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping(params = {"email"}, path = "")
  public ResponseEntity<Applicant> findByEmail(final @RequestParam("email") String email) {
    return applicantService.getApplicantByEmail(email)
        .map(applicant -> ResponseEntity.ok(applicant))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/{applicantId}")
  public ResponseEntity<Applicant> getApplicant(final @PathVariable("applicantId") Long applicantId) {
    return applicantService.getApplicant(applicantId)
        .map(applicant ->  ResponseEntity.ok(applicant))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{applicantId}")
  public void deleteApplicant(final @PathVariable("applicantId") Long applicantId) {
    applicantService.deleteApplicant(applicantId);
  }

  @ExceptionHandler(ApplicantExistsException.class)
  public ResponseEntity<Void> handleApplicantExists() {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .build();
  }
}
