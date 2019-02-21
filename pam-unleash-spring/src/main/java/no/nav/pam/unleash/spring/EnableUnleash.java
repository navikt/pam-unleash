package no.nav.pam.unleash.spring;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Requires @Value("${UNLEASH_API_URL}") to be resolveable, unless EnableUnleash#fakeUnleash is true
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(UnleashConfiguration.class)
public @interface EnableUnleash {

    boolean fakeUnleash() default false;

    String[] enabledFakeToggles() default {};

}
