package dev.abarmin.spring.tdd.workshop.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Aleksandr Barmin
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Applicant {
  @Id
  @GeneratedValue
  private Long applicantId;

  @Valid
  @NotNull
  @Embedded
  private Person person;

  @Valid
  @NotNull
  @Embedded
  private ContactPoint contactPoint;
}
