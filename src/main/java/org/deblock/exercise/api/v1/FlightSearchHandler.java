package org.deblock.exercise.api.v1;

import lombok.extern.log4j.Log4j2;
import org.deblock.exercise.api.v1.contract.FlightSearchRequest;
import org.deblock.exercise.api.v1.contract.FlightSearchResult;
import org.deblock.exercise.api.v1.contract.Supplier;
import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.flight.SearchProvider;
import org.deblock.exercise.search.FlightSearchService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyExtractor;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@Log4j2
public class FlightSearchHandler {
    private final Map<SearchProvider, Supplier> searchProviderConverter = Map.of(SearchProvider.ToughJet, Supplier.ToughJet, SearchProvider.CrazyAir, Supplier.CrazyAir);
    private final FlightSearchService service;

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public FlightSearchHandler(FlightSearchService service) {
        this.service = service;
    }

    private FlightSearchResult convert(Flight flight) {
        return new FlightSearchResult(
                flight.airline(),
                searchProviderConverter.get(flight.supplier()),
                flight.price().compute(),
                flight.origin(),
                flight.destination(),
                flight.departureDate(),
                flight.returnDate()
        );
    }

    public Mono<ServerResponse> searchFlight(ServerRequest request) {
        FlightSearchRequest searchRequest = request.body(this::extract);
        log.info(searchRequest);
        Mono<List<Flight>> futureFlights = service.search(searchRequest.origin(), searchRequest.destination(), searchRequest.departureDate(), searchRequest.returnDate(), searchRequest.numberOfPassenger());
        Mono<List<FlightSearchResult>> result = futureFlights.map(flights -> flights.stream().map(this::convert).toList());
        log.info(result);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(result, new ParameterizedTypeReference<List<FlightSearchResult>>() {
        });
    }

    private FlightSearchRequest extract(ServerHttpRequest serverHttpRequest, BodyExtractor.Context context) {
        MultiValueMap<String, String> queryParams = serverHttpRequest.getQueryParams();
        return new FlightSearchRequest(
                queryParams.getFirst("origin"),
                queryParams.getFirst("destination"),
                LocalDate.parse(queryParams.getFirst("departureDate"), formatter),
                LocalDate.parse(queryParams.getFirst("returnDate"), formatter),
                Short.valueOf(queryParams.getFirst("numberOfPassenger"))
        );
    }
}
