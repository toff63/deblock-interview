package org.deblock.exercise.search;

import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.flight.Price;
import org.deblock.exercise.flight.SearchProvider;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class FlightSearchService {
    public List<Flight> search(String origin, String destination, LocalDateTime departureDate, LocalDateTime returnDate, Short numberOfPassenger) {
        // validate input
        // perform parallel search
        // aggregate results: remove duplicates?, only keep the lowest fares?
        return List.of(new Flight(origin, destination, departureDate.toInstant(ZoneOffset.UTC), returnDate.toInstant(ZoneOffset.UTC), numberOfPassenger, "Air France", SearchProvider.CrazyAir, Optional.empty(),
                new Price(new BigDecimal(123), Optional.empty(), Optional.empty())));
    }
}
