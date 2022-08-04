package dev.abarmin.spring.tdd.workshop.service;

import dev.abarmin.spring.tdd.workshop.model.Applicant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Aleksandr Barmin
 */
public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {
  @Query(value = "from Applicant a where a.contactPoint.electronicAddress.email = ?1")
  Optional<Applicant> getApplicantByEmail(String email);
}
