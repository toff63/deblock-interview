package org.deblock.exercise.flightsuppliers.crazyair;

import java.time.LocalDate;

public record CrazyAirRequest(String origin, String destination, LocalDate departureDate, LocalDate returnDate,
                              Short passengerCount) {
}
