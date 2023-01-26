package org.deblock.exercise.stubs;

import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.flight.Price;
import org.deblock.exercise.flight.SearchProvider;
import org.deblock.exercise.flightsuppliers.FlightSupplierResponseConverter;
import org.deblock.exercise.search.FlightSearchRequest;

import java.util.Optional;

public class GenericConverter implements FlightSupplierResponseConverter<GenericRequest, GenericResponse> {
    @Override
    public GenericRequest toRequest(FlightSearchRequest searchRequest) {
        return new GenericRequest();
    }

    @Override
    public Flight toFlight(GenericRequest genericRequest, GenericResponse response) {
        return new Flight(
                response.origin(),
                response.destination(),
                response.departureDate(),
                response.returnDate(),
                response.numberOfPassenger(),
                response.airline(),
                SearchProvider.CrazyAir,
                Optional.empty(),
                Price.of(response.price())
        );
    }
}
