package org.deblock.exercise.api.v1;

import lombok.extern.log4j.Log4j2;
import org.deblock.exercise.api.v1.contract.FlightSearchResult;
import org.deblock.exercise.api.v1.contract.InputValidator;
import org.deblock.exercise.api.v1.contract.Supplier;
import org.deblock.exercise.exception.DeblockValidationException;
import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.flight.SearchProvider;
import org.deblock.exercise.functional.Either;
import org.deblock.exercise.search.FlightSearchRequest;
import org.deblock.exercise.search.FlightSearchService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * Class responsible for exposing flight search service through HTTP.
 */
@Controller
@Log4j2
public class FlightSearchHandler {
    private final Map<SearchProvider, Supplier> searchProviderConverter = Map.of(SearchProvider.ToughJet, Supplier.ToughJet, SearchProvider.CrazyAir, Supplier.CrazyAir);
    private final FlightSearchService service;
    private final InputValidator validator;

    public FlightSearchHandler(FlightSearchService service, InputValidator validator) {
        this.service = service;
        this.validator = validator;
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
        Either<String, FlightSearchRequest> searchRequestOrError = request.body((serverHttpRequest, extractor) ->
                validator.extractAndValidate(serverHttpRequest.getQueryParams().toSingleValueMap())
        );
        return searchRequestOrError.map(
                this::handleMissingParameterError, this::handleRequest
        );
    }

    private Mono<ServerResponse> handleMissingParameterError(String error) {
        return ServerResponse
                .unprocessableEntity()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.fromSupplier(() -> error), String.class);
    }

    private Mono<ServerResponse> handleRequest(FlightSearchRequest searchRequest) {
        try {
            Mono<List<Flight>> futureFlights = service.search(searchRequest);
            Mono<List<FlightSearchResult>> result = futureFlights.map(flights -> flights.stream().map(this::convert).toList());
            result.onErrorReturn(List.of());
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(result, new ParameterizedTypeReference<>() {
            });
        } catch (DeblockValidationException e) {
            Mono<String> errors = Mono.fromSupplier(e::getMessage);
            return ServerResponse.unprocessableEntity().contentType(MediaType.APPLICATION_JSON).body(errors, new ParameterizedTypeReference<String>() {
            });
        } catch (Throwable t) {
            log.error("Unhandled exception on search with request: " + searchRequest, t);
            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Mono.fromSupplier(() -> "Sorry an unexpected error occurred."), String.class);
        }

    }


}
