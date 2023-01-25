package org.deblock.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.deblock.exercise.flightsuppliers.crazyair.CrazyAirConverter;
import org.deblock.exercise.flightsuppliers.crazyair.CrazyAirSupplier;
import org.deblock.exercise.flightsuppliers.toughjet.ToughJetConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Profile("test")
public class SupplierTestConfiguration {

    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;

    public SupplierTestConfiguration(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClientBuilder = webClientBuilder;
        this.objectMapper = objectMapper;
    }

    @Bean
    public ToughJetSupplierStub toughJetSupplier() {
        return new ToughJetSupplierStub(webClientBuilder, toughJetConverter(), objectMapper);
    }

    @Bean
    public ToughJetConverter toughJetConverter() {
        return new ToughJetConverter();
    }

    @Bean
    @Primary
    public CrazyAirSupplier crazyAirSupplier() {
        return new CrazyAirSupplierStub(webClientBuilder, crazyAirConverter(), objectMapper);
    }

    @Bean
    public CrazyAirConverter crazyAirConverter() {
        return new CrazyAirConverter();
    }
}
