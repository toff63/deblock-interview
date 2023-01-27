package org.deblock.exercise.flightsuppliers;

import lombok.extern.log4j.Log4j2;
import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.search.FlightSearchRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Log4j2
public class SuppliedFlightValidator {
    public boolean isValidFlightResponse(Flight flight, FlightSearchRequest searchRequest) {
        List<String> errors = new ArrayList<>();
        if (!flight.origin().equalsIgnoreCase(searchRequest.origin())) {
            errors.add("origin is different from requested");
        }
        if (!flight.destination().equalsIgnoreCase(searchRequest.destination())) {
            errors.add("destination is different from requested");
        }
        if (flight.departureDate() == null ||
                !LocalDate.ofInstant(flight.departureDate(), ZoneId.of("UTC")).equals(searchRequest.departureDate())) {
            errors.add("departureDate is different from requested");
        }
        if (flight.returnDate() == null ||
                !LocalDate.ofInstant(flight.returnDate(), ZoneId.of("UTC")).equals(searchRequest.returnDate())) {
            errors.add("returnDate is different from requested");
        }
        if (!Objects.equals(flight.numberOfPassenger(), searchRequest.numberOfPassenger())) {
            errors.add("numberOfPassenger is different from requested");
        }
        if (flight.price() == null || flight.price().compute().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("flight price is lower than 0");
        }
        if (errors.size() > 0) {
            log.warn("Invalid flight detected:\n" + errors + "\nRequest: " + searchRequest + "\nFlight received: " + flight);
            return false;
        }
        return true;
    }
}
