package org.deblock.exercise.stubs;

import org.deblock.exercise.exception.SupplierException;
import org.deblock.exercise.flightsuppliers.FlightSupplier;
import org.deblock.exercise.flightsuppliers.FlightSupplierResponseConverter;
import org.deblock.exercise.flightsuppliers.SuppliedFlightValidator;
import org.deblock.exercise.functional.Either;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;


public class GenericSupplier extends FlightSupplier<GenericRequest, GenericResponse> {
    private Either<SupplierException, List<GenericResponse>> responses;

    public GenericSupplier(WebClient.Builder builder, Either<SupplierException, List<GenericResponse>> responses) {
        super(builder, new SuppliedFlightValidator());
        this.responses = responses;
    }

    @Override
    protected FlightSupplierResponseConverter<GenericRequest, GenericResponse> getConverter() {
        return new GenericConverter();
    }

    @Override
    protected Flux<GenericResponse> callSupplier(GenericRequest request) {
        return responses.map(Flux::error, Flux::fromIterable);
    }
}
