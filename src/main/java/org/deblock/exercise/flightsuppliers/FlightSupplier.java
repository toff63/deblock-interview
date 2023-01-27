package org.deblock.exercise.flightsuppliers;

import lombok.extern.log4j.Log4j2;
import org.deblock.exercise.exception.SupplierException;
import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.search.FlightSearchRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Template class with supplier boilerplate code.
 *
 * @param <Request>  Supplier API request type
 * @param <Response> Supplier API response type
 */
@Log4j2
public abstract class FlightSupplier<Request extends SupplierRequest, Response extends SupplierResponse> {
    private final SuppliedFlightValidator suppliedFlightValidator;

    public FlightSupplier(WebClient.Builder builder, SuppliedFlightValidator suppliedFlightValidator) {
        this.suppliedFlightValidator = suppliedFlightValidator;
        builder.filter(errorHandler());
    }

    public Flux<Optional<Flight>> findFlight(FlightSearchRequest searchRequest) {
        Request request = getConverter().toRequest(searchRequest);
        return callSupplier(request).map(res -> getConverter().toFlight(request, res))
                .filter(flight -> suppliedFlightValidator.isValidFlightResponse(flight, searchRequest))
                .map(Optional::ofNullable)
                .doOnError(this::logShit)
                .onErrorReturn(Optional.empty());
    }

    private void logShit(Throwable t) {
        log.warn("Provider " + this.getClass().getSimpleName() + " call failed", t);
    }

    protected abstract FlightSupplierResponseConverter<Request, Response> getConverter();

    protected abstract Flux<Response> callSupplier(Request request);

    public static ExchangeFilterFunction errorHandler() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().is5xxServerError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new SupplierException(errorBody)));
            } else if (clientResponse.statusCode().is4xxClientError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new SupplierException(errorBody)));
            } else {
                return Mono.just(clientResponse);
            }
        });
    }

}
