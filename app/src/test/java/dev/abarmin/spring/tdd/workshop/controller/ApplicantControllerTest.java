package dev.abarmin.spring.tdd.workshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.spring.tdd.workshop.model.Applicant;
import dev.abarmin.spring.tdd.workshop.model.Person;
import dev.abarmin.spring.tdd.workshop.model.PersonName;
import dev.abarmin.spring.tdd.workshop.service.ApplicantService;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Aleksandr Barmin
 */
@WebMvcTest
class ApplicantControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ApplicantService applicantService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void contextShouldStart() {
    assertThat(mockMvc)
        .isNotNull()
        .withFailMessage("Context should start");
  }

  @Test
  void shouldBePossibleToCreateAnApplicant() throws Exception {
    final Applicant applicant = Applicant.builder()
        .person(Person.builder()
            .personName(PersonName.builder()
                .firstName("Test")
                .build())
            .build())
        .build();

    when(applicantService.save(any(Applicant.class))).thenAnswer(inv -> {
      final Applicant param = inv.getArgument(0);
      param.setApplicantId(10L);
      return param;
    });

    mockMvc.perform(post("/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(applicant))
        )
        .andExpect(status().isCreated())
        .andExpect(header().string(HttpHeaders.LOCATION, startsWith("/applicants/")));

    verify(applicantService, times(1)).save(any(Applicant.class));
  }

  @Test
  void get_shouldReturn404WhenNotFound() throws Exception {
    when(applicantService.getApplicant(10L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/applicants/{applicantId}", 10))
        .andExpect(status().isNotFound());

    verify(applicantService, times(1)).getApplicant(eq(10L));
  }
}