package org.deblock.exercise.api.v1.contract;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlightSearchRequestTest {
    private static Validator validator;
    private final LocalDate today = LocalDate.now(ZoneId.of("UTC"));

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void originIsNotAIATACode() {
        Short nbPassenger = 2;
        FlightSearchRequest request = new FlightSearchRequest(
                "London",
                "JFK",
                today.plusDays(1),
                today.plusDays(15),
                nbPassenger);

        Set<ConstraintViolation<FlightSearchRequest>> constraintViolations =
                validator.validate(request);

        assertEquals(1, constraintViolations.size());
        assertEquals("size must be between 3 and 3", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void departureDateIsInThePast() {
        Short nbPassenger = 2;
        FlightSearchRequest request = new FlightSearchRequest(
                "LHR",
                "JFK",
                today.minusDays(1),
                today.plusDays(15),
                nbPassenger);

        Set<ConstraintViolation<FlightSearchRequest>> constraintViolations =
                validator.validate(request);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be a date in the present or in the future", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void numberOfPassengerShouldBeGreaterThan0() {
        Short nbPassenger = 0;
        FlightSearchRequest request = new FlightSearchRequest(
                "LHR",
                "JFK",
                today.plusDays(1),
                today.plusDays(15),
                nbPassenger);

        Set<ConstraintViolation<FlightSearchRequest>> constraintViolations =
                validator.validate(request);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be greater than or equal to 1", constraintViolations.iterator().next().getMessage());
    }
}