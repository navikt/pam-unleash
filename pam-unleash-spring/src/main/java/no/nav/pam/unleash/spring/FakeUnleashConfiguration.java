package no.nav.pam.unleash.spring;

import no.finn.unleash.FakeUnleash;
import no.finn.unleash.Unleash;
import no.nav.pam.unleash.UnleashProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Configuration
class FakeUnleashConfiguration implements ImportAware {


    private AnnotationAttributes enableUnleash;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {

        enableUnleash = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableUnleash.class.getName(), false));

    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public Unleash unleash() {

        FakeUnleash fakeUnleash = new FakeUnleash();
        fakeUnleash.enable(enableUnleash.getStringArray("enabledFakeToggles"));
        UnleashProvider.initialize(fakeUnleash);
        return fakeUnleash;
    }

}
