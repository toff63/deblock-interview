package org.deblock.exercise.flightsuppliers;

import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.search.FlightSearchRequest;

public interface FlightSupplierResponseConverter<SupplierRequest, SupplierResult> {

    public SupplierRequest toRequest(FlightSearchRequest searchRequest);

    public Flight toFlight(SupplierRequest request, SupplierResult result);
}
