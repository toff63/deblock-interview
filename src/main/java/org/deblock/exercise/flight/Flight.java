package org.deblock.exercise.flight;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public record Flight (
    String origin,
    String destination,
    LocalDateTime departureDate,
    LocalDateTime returnDate,
    Short numberOfPassenger,
    String airline,
    SearchProvider supplier,
    Optional<CabinClass> cabinClass,
    Price price){
}
