package org.deblock.exercise.api.v1.contract;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.deblock.exercise.serializer.IsoDateTimeDeserializer;
import org.deblock.exercise.serializer.IsoDateTimeSerializer;

import java.math.BigDecimal;
import java.time.Instant;

public record FlightSearchResult(
        String airline,
        Supplier supplier,
        BigDecimal fare,
        String departureAirportCode,
        String destinationAirportCode,

        @JsonSerialize(using = IsoDateTimeSerializer.class)
        @JsonDeserialize(using = IsoDateTimeDeserializer.class)
        Instant departureDate,
        @JsonSerialize(using = IsoDateTimeSerializer.class)
        @JsonDeserialize(using = IsoDateTimeDeserializer.class)
        Instant arrivalDate) {
}
