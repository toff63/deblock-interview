package org.deblock.exercise;

import org.deblock.exercise.api.v1.contract.FlightSearchResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class FlightSearchTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testSearch() {
        List<FlightSearchResult> expected = List.of();
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/search?origin=LHR&destination=JFK&departureDate=2023-02-02&returnDate=2023-02-28&numberOfPassenger=1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                // Check first airline
                .jsonPath("$.[0].airline").isEqualTo("Air France")
                .jsonPath("$.[0].supplier").isEqualTo("CrazyAir")
                .jsonPath("$.[0].fare").isEqualTo("1234.56")
                .jsonPath("$.[0].departureAirportCode").isEqualTo("LHR")
                .jsonPath("$.[0].destinationAirportCode").isEqualTo("JFK")
                .jsonPath("$.[0].departureDate").isEqualTo("2023-02-02T06:22:00Z[Europe/London]")
                .jsonPath("$.[0].arrivalDate").isEqualTo("2023-02-02T16:13:00Z[Europe/London]")
                // Check second airline
                .jsonPath("$.[1].airline").isEqualTo("Delta Airline")
                .jsonPath("$.[1].supplier").isEqualTo("CrazyAir")
                .jsonPath("$.[1].fare").isEqualTo("1267.56")
                .jsonPath("$.[1].departureAirportCode").isEqualTo("LHR")
                .jsonPath("$.[1].destinationAirportCode").isEqualTo("JFK")
                .jsonPath("$.[1].departureDate").isEqualTo("2023-02-02T08:22:00Z[Europe/London]")
                .jsonPath("$.[1].arrivalDate").isEqualTo("2023-02-02T20:13:00Z[Europe/London]")
                // Check third airline
                .jsonPath("$.[2].airline").isEqualTo("BMI")
                .jsonPath("$.[2].supplier").isEqualTo("CrazyAir")
                .jsonPath("$.[2].fare").isEqualTo("1300.56")
                .jsonPath("$.[2].departureAirportCode").isEqualTo("LHR")
                .jsonPath("$.[2].destinationAirportCode").isEqualTo("JFK")
                .jsonPath("$.[2].departureDate").isEqualTo("2023-02-02T07:42:00Z[Europe/London]")
                .jsonPath("$.[2].arrivalDate").isEqualTo("2023-02-02T15:23:00Z[Europe/London]")
                // Check fourth airline
                .jsonPath("$.[3].airline").isEqualTo("British Airways")
                .jsonPath("$.[3].supplier").isEqualTo("ToughJet")
                .jsonPath("$.[3].fare").isEqualTo("1878.32")
                .jsonPath("$.[3].departureAirportCode").isEqualTo("LHR")
                .jsonPath("$.[3].destinationAirportCode").isEqualTo("JFK")
                .jsonPath("$.[3].departureDate").isEqualTo("2023-02-02T20:22:00Z[Europe/London]")
                .jsonPath("$.[3].arrivalDate").isEqualTo("2023-02-02T23:22:00Z[Europe/London]")
                // Check fifth airline
                .jsonPath("$.[4].airline").isEqualTo("American Airline")
                .jsonPath("$.[4].supplier").isEqualTo("ToughJet")
                .jsonPath("$.[4].fare").isEqualTo(1981.04)
                .jsonPath("$.[4].departureAirportCode").isEqualTo("LHR")
                .jsonPath("$.[4].destinationAirportCode").isEqualTo("JFK")
                .jsonPath("$.[4].departureDate").isEqualTo("2023-02-02T23:22:00Z[Europe/London]")
                .jsonPath("$.[4].arrivalDate").isEqualTo("2023-02-03T02:22:00Z[Europe/London]")
        ;
    }

}
