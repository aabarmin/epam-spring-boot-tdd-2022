package dev.abarmin.spring.tdd.workshop.model;

import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
@Builder
public class PersonName {
  private String firstName;
  private String lastName;
  private String middleName;
}
