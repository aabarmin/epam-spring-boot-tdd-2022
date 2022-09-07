package dev.abarmin.spring.tdd.workshop.model;

import javax.persistence.Embedded;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
  @Valid
  @NotNull
  @Embedded
  private PersonName personName;
}
