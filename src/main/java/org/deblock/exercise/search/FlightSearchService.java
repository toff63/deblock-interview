package org.deblock.exercise.search;

import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.flightsuppliers.FlightSupplier;
import org.deblock.exercise.flightsuppliers.SupplierRequest;
import org.deblock.exercise.flightsuppliers.SupplierResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@Service
public class FlightSearchService {
    private final List<FlightSupplier<? extends SupplierRequest, ? extends SupplierResponse>> suppliers;

    public FlightSearchService(List<FlightSupplier<? extends SupplierRequest, ? extends SupplierResponse>> suppliers) {
        this.suppliers = suppliers;
    }

    private Flux<Flight> orderByFair(Flux<Flight> flights) {
        return flights.sort(Comparator.comparing(f -> f.price().compute()));
    }


    // TODO add tests
    public Mono<List<Flight>> search(String origin, String destination, LocalDate departureDate, LocalDate returnDate, Short numberOfPassenger) {
        // TODO  validate input
        // Solution is parallel and return everything at once
        Function<FlightSupplier<? extends SupplierRequest, ? extends SupplierResponse>, Flux<Flight>> call = supplier -> supplier.findFlight(origin, destination, departureDate, returnDate, numberOfPassenger);
        List<Flux<Flight>> futureFlights = suppliers.stream()
                .map(call)
                .toList();
        return orderByFair(Flux.concat(futureFlights)).collectList();
    }
}
