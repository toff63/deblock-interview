package org.deblock.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.deblock.exercise.flightsuppliers.toughjet.ToughJetConverter;
import org.deblock.exercise.flightsuppliers.toughjet.ToughJetRequest;
import org.deblock.exercise.flightsuppliers.toughjet.ToughJetResponse;
import org.deblock.exercise.flightsuppliers.toughjet.ToughJetSupplier;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ToughJetSupplierStub extends ToughJetSupplier {
    private final ObjectMapper objectMapper;

    public ToughJetSupplierStub(WebClient.Builder builder, ToughJetConverter converter, ObjectMapper objectMapper) {
        super(builder, converter);
        this.objectMapper = objectMapper;
    }

    String serviceResponse = """
            [
              {
                "carrier": "American Airline",
                "basePrice": 2345.6,
                "tax": 130,
                "discount": 10,
                "departureAirportName": "LHR",
                "arrivalAirportName": "JFK",
                "outboundDateTime": "2023-02-02T23:22:00Z",
                "inboundDateTime": "2023-02-03T02:22:00Z"
              },
              {
                "carrier": "British Airways",
                "basePrice": 2145.6,
                "tax": 160,
                "discount": 5,
                "departureAirportName": "LHR",
                "arrivalAirportName": "JFK",
                "outboundDateTime": "2023-02-02T20:22:00Z",
                "inboundDateTime": "2023-02-02T23:22:00Z"
              }
            ]
            """;

    @Override
    protected Flux<ToughJetResponse> callSupplier(ToughJetRequest crazyAirRequest) {
        try {
            List<ToughJetResponse> response = objectMapper.readerForListOf(ToughJetResponse.class).readValue(serviceResponse.getBytes(StandardCharsets.UTF_8));
            return Flux.fromIterable(response);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse hard coded CrazyAir response", e);
        }
    }
}
