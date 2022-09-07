package dev.abarmin.spring.tdd.workshop.bdd;

import io.cucumber.junit.CucumberOptions;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * @author Aleksandr Barmin
 */
@Suite
@Testable
@IncludeEngines("cucumber")
@CucumberOptions(glue = {
    "dev.abarmin.spring.tdd.workshop.bdd.config",
    "dev.abarmin.spring.tdd.workshop.bdd.stepdefs"
})
@SelectClasspathResource("dev/abarmin/spring/tdd/workshop/bdd/bdd.feature")
public class EndToEndIT {

}
