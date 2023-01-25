package org.deblock.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.deblock.exercise.flightsuppliers.crazyair.CrazyAirConverter;
import org.deblock.exercise.flightsuppliers.crazyair.CrazyAirRequest;
import org.deblock.exercise.flightsuppliers.crazyair.CrazyAirResponse;
import org.deblock.exercise.flightsuppliers.crazyair.CrazyAirSupplier;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CrazyAirSupplierStub extends CrazyAirSupplier {

    private final ObjectMapper objectMapper;

    public CrazyAirSupplierStub(WebClient.Builder builder, CrazyAirConverter converter, ObjectMapper objectMapper) {
        super(builder, converter);
        this.objectMapper = objectMapper;
    }

    // TODO move json to a file in resources
    // TODO make the stub parameterizable to simulate various scenarios
    private final String serviceResponse = """
                [
                  {
                    "airline": "Air France",
                    "price": 1234.56,
                    "cabinclass": "E",
                    "departureAirportCode": "LHR",
                    "destinationAirportCode": "JFK",
                    "departureDate": "2023-02-02T06:22:00",
                    "arrivalDate": "2023-02-02T16:13:00"
                  },
                  {
                    "airline": "BMI",
                    "price": 1300.56,
                    "cabinclass": "E",
                    "departureAirportCode": "LHR",
                    "destinationAirportCode": "JFK",
                    "departureDate": "2023-02-02T07:42:00",
                    "arrivalDate": "2023-02-02T15:23:00"
                  },
                  {
                    "airline": "Delta Airline",
                    "price": 1267.56,
                    "cabinclass": "B",
                    "departureAirportCode": "LHR",
                    "destinationAirportCode": "JFK",
                    "departureDate": "2023-02-02T08:22:00",
                    "arrivalDate": "2023-02-02T20:13:00"
                  }
                ]
            """;

    @Override
    protected Flux<CrazyAirResponse> callSupplier(CrazyAirRequest crazyAirRequest) {
        try {
            List<CrazyAirResponse> response = objectMapper.readerForListOf(CrazyAirResponse.class).readValue(serviceResponse.getBytes(StandardCharsets.UTF_8));
            return Flux.fromIterable(response);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse hard coded CrazyAir response", e);
        }
    }
}
