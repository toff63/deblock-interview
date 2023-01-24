package org.deblock.exercise.flight;

import java.time.Instant;
import java.util.Optional;

public record Flight(
        String origin,
        String destination,
        Instant departureDate,
        Instant returnDate,
        Short numberOfPassenger,
        String airline,
        SearchProvider supplier,
        Optional<CabinClass> cabinClass,
        Price price) {
}
