package org.deblock.exercise.search;

import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.flightsuppliers.crazyair.CrazyAirRequest;
import org.deblock.exercise.flightsuppliers.crazyair.CrazyAirSupplier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class FlightSearchService {
    private final CrazyAirSupplier crazyAirSupplier;

    public FlightSearchService(CrazyAirSupplier crazyAirSupplier) {
        this.crazyAirSupplier = crazyAirSupplier;
    }

    private Flux<Flight> orderByFair(Flux<Flight> flights) {
        return flights.sort(Comparator.comparing(f -> f.price().compute()));
    }

    public Mono<List<Flight>> search(String origin, String destination, LocalDate departureDate, LocalDate returnDate, Short numberOfPassenger) {
        // TODO  validate input
        // TODO  aggregate results: remove duplicates?, only keep the lowest fares?
        // Solution is parallel and return everything at once
        Flux<Flight> response = crazyAirSupplier.findFlight(new CrazyAirRequest(origin, destination, departureDate, returnDate, numberOfPassenger));
        Flux<Flight> response2 = crazyAirSupplier.findFlight(new CrazyAirRequest(origin, destination, departureDate, returnDate, numberOfPassenger));
        List<Flux<Flight>> l = List.of(response, response2);
        return orderByFair(Flux.concat(l)).collectList();
    }
}
