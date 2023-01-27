package org.deblock.exercise.flightsuppliers;

import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.flight.Price;
import org.deblock.exercise.flight.SearchProvider;
import org.deblock.exercise.search.FlightSearchRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;

class SuppliedFlightValidatorTest {

    private final LocalDate today = LocalDate.now(ZoneId.of("UTC"));
    private final SuppliedFlightValidator validator = new SuppliedFlightValidator();
    private final FlightSearchRequest request = new FlightSearchRequest(
            "LHR",
            "JFK",
            today.plusDays(1),
            today.plusDays(15),
            Short.valueOf("2"));

    @Test
    void testValidFlight() {
        Flight flight = new Flight(
                request.origin(),
                request.destination(),
                Instant.ofEpochSecond(request.departureDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                Instant.ofEpochSecond(request.returnDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                request.numberOfPassenger(),
                "British Airways",
                SearchProvider.CrazyAir,
                Optional.empty(),
                Price.of(BigDecimal.valueOf(1234.56))
        );

        Assertions.assertTrue(validator.isValidFlightResponse(flight, request));
    }

    @Test
    void testInvalidFlightOrigin() {
        Flight flight = new Flight(
                "CDG",
                request.destination(),
                Instant.ofEpochSecond(request.departureDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                Instant.ofEpochSecond(request.returnDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                request.numberOfPassenger(),
                "British Airways",
                SearchProvider.CrazyAir,
                Optional.empty(),
                Price.of(BigDecimal.valueOf(1234.56))
        );
        Assertions.assertFalse(validator.isValidFlightResponse(flight, request));
    }

    @Test
    void testInvalidFlightDestination() {
        Flight flight = new Flight(
                request.origin(),
                "CDG",
                Instant.ofEpochSecond(request.departureDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                Instant.ofEpochSecond(request.returnDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                request.numberOfPassenger(),
                "British Airways",
                SearchProvider.CrazyAir,
                Optional.empty(),
                Price.of(BigDecimal.valueOf(1234.56))
        );
        Assertions.assertFalse(validator.isValidFlightResponse(flight, request));
    }

    @Test
    void testInvalidFlightDepartureDate() {
        Flight flight = new Flight(
                request.origin(),
                request.destination(),
                Instant.ofEpochSecond(request.departureDate().plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                Instant.ofEpochSecond(request.returnDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                request.numberOfPassenger(),
                "British Airways",
                SearchProvider.CrazyAir,
                Optional.empty(),
                Price.of(BigDecimal.valueOf(1234.56))
        );
        Assertions.assertFalse(validator.isValidFlightResponse(flight, request));
    }

    @Test
    void testInvalidFlightReturnDate() {
        Flight flight = new Flight(
                request.origin(),
                request.destination(),
                Instant.ofEpochSecond(request.departureDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                Instant.ofEpochSecond(request.returnDate().plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                request.numberOfPassenger(),
                "British Airways",
                SearchProvider.CrazyAir,
                Optional.empty(),
                Price.of(BigDecimal.valueOf(1234.56))
        );
        Assertions.assertFalse(validator.isValidFlightResponse(flight, request));
    }

    @Test
    void testInvalidFlightNumberOfPassengers() {
        Flight flight = new Flight(
                request.origin(),
                request.destination(),
                Instant.ofEpochSecond(request.departureDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                Instant.ofEpochSecond(request.returnDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                Short.valueOf("1"),
                "British Airways",
                SearchProvider.CrazyAir,
                Optional.empty(),
                Price.of(BigDecimal.valueOf(1234.56))
        );
        Assertions.assertFalse(validator.isValidFlightResponse(flight, request));
    }
}