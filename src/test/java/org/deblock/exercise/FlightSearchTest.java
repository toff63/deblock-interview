package org.deblock.exercise;

import org.assertj.core.api.Assertions;
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
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

}
