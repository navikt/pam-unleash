package no.nav.pam.unleash.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

@Configuration
public class UnleashConfiguration implements ImportSelector {



    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableUnleash.class.getName(), false));

        if (attributes.getBoolean("fakeUnleash")) {
            return new String[]{"no.nav.pam.unleash.spring.FakeUnleashConfiguration"};
        }else {
            return new String[]{"no.nav.pam.unleash.spring.DefaultUnleashConfiguration"};
        }
    }
}
