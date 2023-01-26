package org.deblock.exercise.search;

import org.deblock.exercise.exception.DeblockValidationException;
import org.deblock.exercise.exception.SupplierException;
import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.flightsuppliers.FlightSupplier;
import org.deblock.exercise.flightsuppliers.SupplierRequest;
import org.deblock.exercise.flightsuppliers.SupplierResponse;
import org.deblock.exercise.functional.Either;
import org.deblock.exercise.stubs.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

class FlightSearchServiceTest {

    private static Validator validator;
    private final GenericConverter converter = new GenericConverter();
    private final LocalDate departureDate = LocalDate.now().plusDays(1);
    private final Instant baseDepartureInstant = Instant.ofEpochSecond(departureDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC));
    private final LocalDate returnDate = LocalDate.now().plusDays(21);
    private final Instant baseReturnInstant = Instant.ofEpochSecond(returnDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC));
    private final String origin = "LHR";
    private final String destination = "JFK";
    private final Short nbPassenger = 2;


    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void searchShouldValidateRequest() {
        FlightSearchService service = new FlightSearchService(List.of(), validator);
        LocalDate departureDate = LocalDate.now().plusDays(1);
        LocalDate returnDate = LocalDate.now().plusDays(21);
        try {
            service.search(new FlightSearchRequest(
                    "NotValid", "JFK", departureDate, returnDate, Short.valueOf("2")
            ));
        } catch (DeblockValidationException e) {
            Assertions.assertEquals("origin size must be between 3 and 3", e.getMessage());
        }
    }

    private GenericResponse buildResponse(String airline, BigDecimal price, Integer departureDateDelta) {
        return new GenericResponse(
                origin,
                destination, baseDepartureInstant.minus(departureDateDelta, ChronoUnit.DAYS),
                baseReturnInstant,
                nbPassenger,
                airline,
                price);
    }

    @Test
    public void searchShouldReturnAggregationOfEachSupplier() {
        GenericResponse responseFromA = buildResponse("AirlineA", BigDecimal.valueOf(1234.4), 0);
        GenericResponse responseFromB = buildResponse("AirlineB", BigDecimal.valueOf(14.4), 0);
        List<FlightSupplier<? extends SupplierRequest, ? extends SupplierResponse>> suppliers = List.of(
                new GenericSupplier(new DummyWebClientBuilder(), Either.right(List.of(responseFromA))),
                new GenericSupplier(new DummyWebClientBuilder(), Either.right(List.of(responseFromB)))
        );
        FlightSearchService service = new FlightSearchService(suppliers, validator);
        List<Flight> flights = service.search(new FlightSearchRequest(
                origin, destination, departureDate, returnDate, nbPassenger
        )).block();
        List<Flight> expected = List.of(converter.toFlight(new GenericRequest(), responseFromB), converter.toFlight(new GenericRequest(), responseFromA)); // ordered by raising price
        Assertions.assertEquals(expected, flights);
    }

    @Test
    public void searchShouldRemoveInvalidFights() {
        GenericResponse invalidFlight = buildResponse("AirlineA", BigDecimal.valueOf(1234.4), 1);
        GenericResponse responseFromB = buildResponse("AirlineB", BigDecimal.valueOf(14.4), 0);

        List<FlightSupplier<? extends SupplierRequest, ? extends SupplierResponse>> suppliers = List.of(
                new GenericSupplier(new DummyWebClientBuilder(), Either.right(List.of(invalidFlight, responseFromB)))
        );
        FlightSearchService service = new FlightSearchService(suppliers, validator);

        List<Flight> flights = service.search(new FlightSearchRequest(
                origin, destination, departureDate, returnDate, nbPassenger
        )).block();
        List<Flight> expected = List.of(converter.toFlight(new GenericRequest(), responseFromB));
        Assertions.assertEquals(expected, flights);
    }

    @Test
    void searchShouldStillReturnWhenOneSupplierFails() {
        GenericResponse responseFromA = buildResponse("AirlineA", BigDecimal.valueOf(1234.4), 0);
        SupplierException responseFromB = new SupplierException("Supplier B failed");
        List<FlightSupplier<? extends SupplierRequest, ? extends SupplierResponse>> suppliers = List.of(
                new GenericSupplier(new DummyWebClientBuilder(), Either.right(List.of(responseFromA))),
                new GenericSupplier(new DummyWebClientBuilder(), Either.left(responseFromB))
        );
        FlightSearchService service = new FlightSearchService(suppliers, validator);

        List<Flight> flights = service.search(new FlightSearchRequest(
                origin, destination, departureDate, returnDate, nbPassenger
        )).block();
        List<Flight> expected = List.of(converter.toFlight(new GenericRequest(), responseFromA));
        Assertions.assertEquals(expected, flights);
    }
}