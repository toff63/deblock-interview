package org.deblock.exercise.flightsuppliers.crazyair;

import org.deblock.exercise.flightsuppliers.FlightSupplier;
import org.deblock.exercise.flightsuppliers.FlightSupplierResponseConverter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class CrazyAirSupplier extends FlightSupplier<CrazyAirRequest, CrazyAirResponse> {
    private final WebClient client;
    private final CrazyAirConverter converter;

    public CrazyAirSupplier(WebClient.Builder builder, CrazyAirConverter converter) {
        // TODO move hard coded url to property file
        super(builder);
        this.client = builder.baseUrl("http://mockbin.org/bin/19a8f504-6d48-44ea-af01-c2f3ba839909").build();
        // URL used to test client errors with more time I would look into writing an automatic test for this use case
        //        this.client = builder.baseUrl("http://mockbin.org/bin/fb60ffb2-a99f-4e31-950b-82c83fb28678").build();

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
