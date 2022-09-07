package dev.abarmin.spring.tdd.workshop.bdd.config;

import dev.abarmin.spring.tdd.workshop.bdd.service.ApplicantService;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Aleksandr Barmin
 */
@CucumberContextConfiguration
public class SpringTestSupport {
  @Configuration
  @ComponentScan(basePackageClasses = ApplicantService.class)
  public static class SpringConfiguration {
    @Bean
    public RestTemplate restTemplate() {
      return new RestTemplateBuilder().build();
    }

    @Bean
    @BaseUrl
    public String baseUrl() {
      return "http://localhost:8080";
    }
  }
}
