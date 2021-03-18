package no.nav.pam.unleash.spring;

import no.finn.unleash.DefaultUnleash;
import no.finn.unleash.FakeUnleash;
import no.finn.unleash.Unleash;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationTest {

    @EnableUnleash(fakeUnleash = true, enabledFakeToggles = {"featureFoo"})
    @Configuration
    static class DummyTestConfiguration {

    }

    @EnableUnleash
    @Configuration
    @PropertySource("classpath:/test.yaml")
    static class DummyProdConfiguration {

    }

    @Test
    void that_fake_configuration_is_honored() {

        ApplicationContext context = new AnnotationConfigApplicationContext(DummyTestConfiguration.class);
        assertThat(context.getBean(Unleash.class)).isInstanceOf(FakeUnleash.class);
    }

    @Test
    void that_fake_configuration_enabled_toggles_is_honored() {

        ApplicationContext context = new AnnotationConfigApplicationContext(DummyTestConfiguration.class);
        Unleash unleash = context.getBean(Unleash.class);
        assertThat(unleash.isEnabled("featureFoo")).isTrue();
        assertThat(unleash.isEnabled("featureBar")).isFalse();
    }

    @Test
    void that_default_configuration_is_honored() {

        ApplicationContext context = new AnnotationConfigApplicationContext(DummyProdConfiguration.class);
        assertThat(context.getBean(Unleash.class)).isInstanceOf(DefaultUnleash.class);
    }

}
