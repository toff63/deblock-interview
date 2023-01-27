package org.deblock.exercise.constraints;

import org.deblock.exercise.search.FlightSearchRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Annotation to validate if a return date is either the same day, either after the return date
 */
public class ConsistentDateParameterValidator implements ConstraintValidator<ConsistentDateParameters, FlightSearchRequest> {
    @Override
    public boolean isValid(FlightSearchRequest value, ConstraintValidatorContext context) {
        return value.returnDate() != null &&
                value.departureDate() != null &&
                (value.departureDate().equals(value.returnDate()) || value.departureDate().isBefore(value.returnDate()));
    }
}
