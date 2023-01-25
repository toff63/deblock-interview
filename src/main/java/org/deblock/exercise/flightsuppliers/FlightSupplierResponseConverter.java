package org.deblock.exercise.flightsuppliers;

import org.deblock.exercise.flight.Flight;

import java.time.LocalDate;

public interface FlightSupplierResponseConverter<SupplierRequest, SupplierResult> {

    public SupplierRequest toRequest(String origin, String destination,
                                     LocalDate departureDate,
                                     LocalDate returnDate, Short numberOfPassenger);

    public Flight toFlight(SupplierRequest request, SupplierResult result);
}
