package org.deblock.exercise.api.v1.contract;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public record FlightSearchRequest(
        @NotNull
        @Size(min = 3, max = 3)
        String origin,
        @NotNull
        @Size(min = 3, max = 3)
        String destination,
        @NotNull
        @FutureOrPresent
        LocalDate departureDate,
        @NotNull
        @FutureOrPresent
        LocalDate returnDate,
        @NotNull
        @Min(1)
        Short numberOfPassenger) {
}
