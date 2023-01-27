package org.deblock.exercise.flightsuppliers.toughjet;

import org.deblock.exercise.flightsuppliers.FlightSupplier;
import org.deblock.exercise.flightsuppliers.FlightSupplierResponseConverter;
import org.deblock.exercise.flightsuppliers.SuppliedFlightValidator;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Class responsible for calling ToughJet API
 */
public class ToughJetSupplier extends FlightSupplier<ToughJetRequest, ToughJetResponse> {
    private final WebClient client;
    private final ToughJetConverter converter;

    public ToughJetSupplier(WebClient.Builder builder, ToughJetConverter converter, SuppliedFlightValidator suppliedFlightValidator) {
        // TODO move hard coded url to property file
        super(builder, suppliedFlightValidator);
        this.client = builder.baseUrl("http://mockbin.org/bin/4ef7310b-307e-42a9-bca6-90b6d3b34510").build();
        //        this.client = builder.baseUrl("http://mockbin.org/bin/fb60ffb2-a99f-4e31-950b-82c83fb28678").build();
        this.converter = converter;
    }

    @Override
    protected FlightSupplierResponseConverter<ToughJetRequest, ToughJetResponse> getConverter() {
        return converter;
    }

    @Override
    protected Flux<ToughJetResponse> callSupplier(ToughJetRequest toughJetRequest) {
        return this.client.get()
                .uri(uriBuilder -> converter.toURI(uriBuilder, toughJetRequest))
                .retrieve()
                .bodyToFlux(ToughJetResponse.class);
    }
}
