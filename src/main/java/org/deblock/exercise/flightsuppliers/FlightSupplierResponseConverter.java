package org.deblock.exercise.flightsuppliers;

import org.deblock.exercise.flight.Flight;

public interface FlightSupplierResponseConverter<SupplierRequest, SupplierResult> {

    public Flight toFlight(SupplierRequest request, SupplierResult result);
}
