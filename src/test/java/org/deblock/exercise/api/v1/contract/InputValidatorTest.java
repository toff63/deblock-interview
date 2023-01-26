package org.deblock.exercise.api.v1.contract;

import org.deblock.exercise.functional.Either;
import org.deblock.exercise.search.FlightSearchRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

class InputValidatorTest {

    private final InputValidator validator = new InputValidator();
    private final Map<String, String> validInput = Map.of(
            "origin", "LHR",
            "destination", "JFK",
            "departureDate", "2023-01-21",
            "returnDate", "2023-02-21",
            "numberOfPassenger", "2");

    @Test
    void extractAndValidateTestMissingParam() {
        String errorMessage = "Request is missing the following parameters: returnDate, origin, destination, numberOfPassenger, departureDate";
        Assertions.assertEquals(Either.left(errorMessage), validator.extractAndValidate(Map.of()));
    }

    @Test
    void extractAndValidateTestEmptyDepartureDate() {
        Map<String, String> inputWithEmptyDepartureDate = new HashMap<>(validInput);
        inputWithEmptyDepartureDate.put("departureDate", "");
        Either<String, FlightSearchRequest> expected = Either.left("DepartureDate and returnDate must be specified");
        Assertions.assertEquals(expected, validator.extractAndValidate(inputWithEmptyDepartureDate));
    }

    @Test
    void extractAndValidateTestEmptyReturnDate() {
        Map<String, String> inputWithEmptyReturnDate = new HashMap<>(validInput);
        inputWithEmptyReturnDate.put("returnDate", "");
        Either<String, FlightSearchRequest> expected = Either.left("DepartureDate and returnDate must be specified");
        Assertions.assertEquals(expected, validator.extractAndValidate(inputWithEmptyReturnDate));
    }

    @Test
    void extractAndValidateTestValidInput() {
        LocalDate departureDate = LocalDate.of(2023, 1, 21);
        LocalDate returnDate = LocalDate.of(2023, 2, 21);
        Either<String, FlightSearchRequest> expected = Either.right(new FlightSearchRequest(
                "LHR", "JFK", departureDate, returnDate, Short.valueOf("2")
        ));
        Assertions.assertEquals(expected, validator.extractAndValidate(validInput));
    }
}