package dev.abarmin.spring.tdd.workshop.model;

import javax.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
@Builder
public class ElectronicAddress {
  @Email
  private String email;
}
