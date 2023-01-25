package org.deblock.exercise.configuration;

import org.deblock.exercise.flightsuppliers.crazyair.CrazyAirConverter;
import org.deblock.exercise.flightsuppliers.crazyair.CrazyAirSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SupplierConfiguration {

    private final WebClient.Builder webClientBuilder;

    public SupplierConfiguration(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Bean
    public CrazyAirSupplier crazyAirSupplier() {
        return new CrazyAirSupplier(webClientBuilder, crazyAirConverter());
    }

    @Bean
    public CrazyAirConverter crazyAirConverter() {
        return new CrazyAirConverter();
    }
}
