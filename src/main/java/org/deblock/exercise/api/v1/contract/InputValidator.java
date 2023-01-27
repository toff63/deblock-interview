package org.deblock.exercise.api.v1.contract;

import org.deblock.exercise.functional.Either;
import org.deblock.exercise.search.FlightSearchRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class validating client sent enough information in the right format to process the request
 */
@Component
public class InputValidator {
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;


    // TODO move messages to a central place and use MessageFormat
    public Either<String, FlightSearchRequest> extractAndValidate(Map<String, String> queryParams) {
        Set<String> missingKeys = new HashSet<>(Set.of("origin", "destination", "departureDate", "returnDate", "numberOfPassenger"));
        missingKeys.removeAll(queryParams.keySet());
        if (missingKeys.size() > 0) {
            return Either.left("Request is missing the following parameters: " + String.join(", ", missingKeys));
        }
        if (ObjectUtils.isEmpty(queryParams.get("departureDate")) || ObjectUtils.isEmpty(queryParams.get("returnDate"))) {
            return Either.left("DepartureDate and returnDate must be specified");
        }
        return Either.right(new FlightSearchRequest(
                queryParams.get("origin"),
                queryParams.get("destination"),
                LocalDate.parse(queryParams.get("departureDate"), formatter),
                LocalDate.parse(queryParams.get("returnDate"), formatter),
                Short.valueOf(queryParams.get("numberOfPassenger"))));
    }
}
