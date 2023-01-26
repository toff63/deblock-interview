package org.deblock.exercise.search;

import org.deblock.exercise.exception.DeblockValidationException;
import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.flightsuppliers.FlightSupplier;
import org.deblock.exercise.flightsuppliers.SupplierRequest;
import org.deblock.exercise.flightsuppliers.SupplierResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Service
public class FlightSearchService {
    private final List<FlightSupplier<? extends SupplierRequest, ? extends SupplierResponse>> suppliers;
    private final Validator validator;

    public FlightSearchService(List<FlightSupplier<? extends SupplierRequest, ? extends SupplierResponse>> suppliers, Validator validator) {
        this.suppliers = suppliers;
        this.validator = validator;
    }

    private Flux<Flight> orderByFair(Flux<Flight> flights) {
        return flights.sort(Comparator.comparing(f -> f.price().compute()));
    }


    public Mono<List<Flight>> search(FlightSearchRequest searchRequest) {
        Set<ConstraintViolation<FlightSearchRequest>> constraintViolations = validator.validate(searchRequest);
        if (constraintViolations.size() > 0) {
            String errorMessage = String.join(", ", constraintViolations.stream().map(violation -> violation.getPropertyPath() + " " + violation.getMessage()).toList());
            throw new DeblockValidationException(errorMessage);
        }

        // Solution is parallel and return everything at once
        Function<FlightSupplier<? extends SupplierRequest, ? extends SupplierResponse>, Flux<Optional<Flight>>> call = supplier -> supplier.findFlight(searchRequest);
        List<Flux<Optional<Flight>>> futureFlights = suppliers.stream()
                .map(call)
                .toList();
        return orderByFair(Flux.concat(futureFlights).filter(Optional::isPresent).map(Optional::get)).collectList();
    }
}
