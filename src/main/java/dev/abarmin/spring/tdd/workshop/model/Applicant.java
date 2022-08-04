package dev.abarmin.spring.tdd.workshop.model;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
@Builder
public class Applicant {
  private UUID applicantId;

  @Valid
  @NotNull
  private Person person;

  @Valid
  @NotNull
  private ContactPoint contactPoint;
}
