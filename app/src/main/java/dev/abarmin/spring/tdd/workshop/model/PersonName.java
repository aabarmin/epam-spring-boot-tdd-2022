package dev.abarmin.spring.tdd.workshop.model;

import javax.validation.constraints.NotEmpty;
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
public class PersonName {
  private String firstName;
  private String lastName;
  private String middleName;
}
