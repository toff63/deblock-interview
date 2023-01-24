package org.deblock.exercise.api.v1.contract;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightSearchResult(
        String airline,
        Supplier supplier,
        BigDecimal fare,
        String departureAirportCode,
        String destinationAirportCode,
        LocalDateTime departureDate,
        LocalDateTime arrivalDate) {
}
