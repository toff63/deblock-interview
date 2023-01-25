package org.deblock.exercise.flightsuppliers.crazyair;

import org.deblock.exercise.flightsuppliers.FlightSupplier;
import org.deblock.exercise.flightsuppliers.FlightSupplierResponseConverter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class CrazyAirSupplier extends FlightSupplier<CrazyAirRequest, CrazyAirResponse> {
    // mocked endpoint: http://mockbin.org/bin/304a82b7-53db-426e-97fd-8c2487aa19a5

    private final WebClient client;
    private final CrazyAirConverter converter;

    public CrazyAirSupplier(WebClient.Builder builder, CrazyAirConverter converter) {
        // TODO move hard coded url to property file
        this.client = builder.baseUrl("http://mockbin.org/bin/304a82b7-53db-426e-97fd-8c2487aa19a5").build();
        this.converter = converter;
    }

    @Override
    protected FlightSupplierResponseConverter<CrazyAirRequest, CrazyAirResponse> getConverter() {
        return converter;
    }

    @Override
    protected Flux<CrazyAirResponse> callSupplier(CrazyAirRequest crazyAirRequest) {
        return this.client.get()
                .uri(uriBuilder -> converter.toURI(uriBuilder, crazyAirRequest))
                .retrieve()
                .bodyToFlux(CrazyAirResponse.class);
    }
}
