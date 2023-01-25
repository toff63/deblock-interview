package org.deblock.exercise.flightsuppliers;

import org.deblock.exercise.flight.Flight;
import reactor.core.publisher.Flux;

public abstract class FlightSupplier<SupplierRequest, SupplierResult> {
    public Flux<Flight> findFlight(SupplierRequest request) {
        return callSupplier(request).map(res -> getConverter().toFlight(request, res));
    }

    protected abstract FlightSupplierResponseConverter<SupplierRequest, SupplierResult> getConverter();

    protected abstract Flux<SupplierResult> callSupplier(SupplierRequest request);
}
