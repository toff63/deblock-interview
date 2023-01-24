package org.deblock.exercise.api.v1.contract;

import java.time.LocalDateTime;

public record FlightSearchRequest(String origin, String destination, LocalDateTime departureDate, LocalDateTime returnDate, Short numberOfPassenger) {
}
