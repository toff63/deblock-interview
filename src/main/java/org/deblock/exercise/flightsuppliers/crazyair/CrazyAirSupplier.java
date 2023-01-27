package org.deblock.exercise.flightsuppliers.crazyair;

import org.deblock.exercise.flightsuppliers.FlightSupplier;
import org.deblock.exercise.flightsuppliers.FlightSupplierResponseConverter;
import org.deblock.exercise.flightsuppliers.SuppliedFlightValidator;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Class responsible for calling CrazyAir API
 */
public class CrazyAirSupplier extends FlightSupplier<CrazyAirRequest, CrazyAirResponse> {
    private final WebClient client;
    private final CrazyAirConverter converter;

    public CrazyAirSupplier(WebClient.Builder builder, CrazyAirConverter converter, SuppliedFlightValidator suppliedFlightValidator) {
        // TODO move hard coded url to property file
        super(builder, suppliedFlightValidator);
        this.client = builder.baseUrl("http://mockbin.org/bin/5a263272-bd54-4688-a017-cd151a7aa0a4").build();
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
