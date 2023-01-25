package org.deblock.exercise.flightsuppliers;

import org.deblock.exercise.flight.Flight;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public abstract class FlightSupplier<Request extends SupplierRequest, Response extends SupplierResponse> {
    public Flux<Flight> findFlight(String origin, String destination,
                                   LocalDate departureDate,
                                   LocalDate returnDate, Short numberOfPassenger) {
        Request request = getConverter().toRequest(origin, destination, departureDate, returnDate, numberOfPassenger);
        return callSupplier(request).map(res -> getConverter().toFlight(request, res));
    }

    protected abstract FlightSupplierResponseConverter<Request, Response> getConverter();

    protected abstract Flux<Response> callSupplier(Request request);
}
