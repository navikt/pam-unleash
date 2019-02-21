package no.nav.pam.unleash.spring;

import no.finn.unleash.DefaultUnleash;
import no.finn.unleash.Unleash;
import no.finn.unleash.strategy.Strategy;
import no.finn.unleash.util.UnleashConfig;
import no.nav.pam.unleash.IsNotProdStrategy;
import no.nav.pam.unleash.UnleashProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Configuration
public class DefaultUnleashConfiguration {

    @Bean
    @Scope(SCOPE_SINGLETON)
    public Unleash unleash(
            @Value("${APP_NAME:}") String appName,
            @Value("${NAIS_APP_NAME:}") String naisAppName,
            @Value("${unleash.api.url}") String unleashApiUrl,
            Strategy... strategies) {
        UnleashConfig config = UnleashConfig.builder()
                .appName(naisAppName != null ? naisAppName : appName)
                .unleashAPI(unleashApiUrl)
                .build();

        UnleashProvider.initialize(new DefaultUnleash(config, strategies));
        return UnleashProvider.get();
    }

    @Bean
    public Strategy isNotProd(){
        return new IsNotProdStrategy();
    }


}
