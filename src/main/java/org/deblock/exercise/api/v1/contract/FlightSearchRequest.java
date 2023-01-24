package org.deblock.exercise.api.v1.contract;

import java.time.LocalDate;

public record FlightSearchRequest(
        String origin, String destination,
        LocalDate departureDate,
        LocalDate returnDate, Short numberOfPassenger) {
}
