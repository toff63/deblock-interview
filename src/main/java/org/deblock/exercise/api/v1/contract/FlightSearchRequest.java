package org.deblock.exercise.api.v1.contract;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record FlightSearchRequest(
        String origin, String destination,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate departureDate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate returnDate, Short numberOfPassenger) {
}
