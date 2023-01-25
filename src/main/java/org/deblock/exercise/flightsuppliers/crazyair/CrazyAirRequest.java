package org.deblock.exercise.flightsuppliers.crazyair;

import org.deblock.exercise.flightsuppliers.SupplierRequest;

import java.time.LocalDate;

public record CrazyAirRequest(
        String origin,
        String destination,
        LocalDate departureDate,
        LocalDate returnDate,
        Short passengerCount) implements SupplierRequest {
}
