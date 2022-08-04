package dev.abarmin.spring.tdd.workshop.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
@Builder
public class ContactPoint {
  @Valid
  @NotNull
  private ElectronicAddress electronicAddress;
}
