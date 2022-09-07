package dev.abarmin.spring.tdd.workshop.bdd.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author Aleksandr Barmin
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseUrl {
}
