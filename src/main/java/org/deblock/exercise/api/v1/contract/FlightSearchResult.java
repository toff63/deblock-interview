package org.deblock.exercise.api.v1.contract;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightSearchResult(
        String airline,
        Supplier supplier,
        BigDecimal fare,
        String departureAirportCode,
        String destinationAirportCode,
        @JsonFormat(pattern = "yyyy-MM-dd")

        LocalDateTime departureDate,
        @JsonFormat(pattern = "yyyy-MM-dd")

        LocalDateTime arrivalDate) {
}
