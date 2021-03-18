package no.nav.pam.unleash;

import no.finn.unleash.strategy.Strategy;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class IsNotProdStrategy implements Strategy {

    private static final List<String> DEFAULT_PROD_ENVIRONMENTS = Arrays.asList("prod-sbs", "prod-fss", "p", "prod-gcp");

    private final boolean isProd;

    public IsNotProdStrategy() {
        isProd = DEFAULT_PROD_ENVIRONMENTS.contains(System.getenv("NAIS_CLUSTER_NAME")) ||
                DEFAULT_PROD_ENVIRONMENTS.contains(System.getenv("FASIT_ENVIRONMENT_NAME"));
    }

    public IsNotProdStrategy(String currentEnvironment, String prodEnvironment) {
        isProd = currentEnvironment.equalsIgnoreCase(prodEnvironment);
    }

    @Override
    public String getName() {
        return "isNotProd";
    }

    @Override
    public boolean isEnabled(Map<String, String> map) {
        return !isProd;
    }

}
