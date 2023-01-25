package org.deblock.exercise.flightsuppliers.crazyair;

import java.math.BigDecimal;
import java.time.LocalDateTime;

enum CrazyAirCabinClass {
    E, B //  E for Economy and B for Business
}

public record CrazyAirResponse(String airline,
                               BigDecimal price, CrazyAirCabinClass cabinclass, String departureAirportCode,
                               String destinationAirportCode,
                               LocalDateTime departureDate, LocalDateTime arrivalDate) {
}
