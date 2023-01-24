package org.deblock.exercise.flightsuppliers;

public interface FlightSupplier<SupplierRequest, SupplierResult> {
    public SupplierResult findFlight(SupplierRequest request);
}
