package org.deblock.exercise.stubs;

import org.deblock.exercise.flightsuppliers.SupplierResponse;

import java.math.BigDecimal;
import java.time.Instant;

public record GenericResponse(
        String origin,
        String destination,
        Instant departureDate,
        Instant returnDate,
        Short numberOfPassenger,
        String airline,
        BigDecimal price
) implements SupplierResponse {
}
