package org.deblock.exercise.flightsuppliers.toughjet;

import org.deblock.exercise.flightsuppliers.FlightSupplier;
import org.deblock.exercise.flightsuppliers.FlightSupplierResponseConverter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class ToughJetSupplier extends FlightSupplier<ToughJetRequest, ToughJetResponse> {
    private final WebClient client;
    private final ToughJetConverter converter;

    public ToughJetSupplier(WebClient.Builder builder, ToughJetConverter converter) {
        // TODO move hard coded url to property file
        super(builder);
        this.client = builder.baseUrl("http://mockbin.org/bin/916cb656-79cc-4a8e-9768-120ceaa9bbc0").build();
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