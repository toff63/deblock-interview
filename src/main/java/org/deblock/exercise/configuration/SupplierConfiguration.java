package org.deblock.exercise.configuration;

import org.deblock.exercise.flightsuppliers.FlightSupplier;
import org.deblock.exercise.flightsuppliers.SuppliedFlightValidator;
import org.deblock.exercise.flightsuppliers.SupplierRequest;
import org.deblock.exercise.flightsuppliers.SupplierResponse;
import org.deblock.exercise.flightsuppliers.crazyair.CrazyAirConverter;
import org.deblock.exercise.flightsuppliers.crazyair.CrazyAirSupplier;
import org.deblock.exercise.flightsuppliers.toughjet.ToughJetConverter;
import org.deblock.exercise.flightsuppliers.toughjet.ToughJetSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Configuration
@Profile("production")
public class SupplierConfiguration {

    private final WebClient.Builder webClientBuilder;

    public SupplierConfiguration(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Bean
    public List<FlightSupplier<? extends SupplierRequest, ? extends SupplierResponse>> getSuppliers() {
        return List.of(crazyAirSupplier(), toughJetSupplier());
    }

    @Bean
    public ToughJetSupplier toughJetSupplier() {
        return new ToughJetSupplier(webClientBuilder, toughJetConverter(), suppliedFlightValidator());
    }

    @Bean
    public ToughJetConverter toughJetConverter() {
        return new ToughJetConverter();
    }

    @Bean
    public CrazyAirSupplier crazyAirSupplier() {
        return new CrazyAirSupplier(webClientBuilder, crazyAirConverter(), suppliedFlightValidator());
    }

    @Bean
    public SuppliedFlightValidator suppliedFlightValidator() {
        return new SuppliedFlightValidator();
    }

    @Bean
    public CrazyAirConverter crazyAirConverter() {
        return new CrazyAirConverter();
    }
}
