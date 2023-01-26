package org.deblock.exercise.flightsuppliers;

import lombok.extern.log4j.Log4j2;
import org.deblock.exercise.exception.SupplierException;
import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.search.FlightSearchRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
public abstract class FlightSupplier<Request extends SupplierRequest, Response extends SupplierResponse> {
    public FlightSupplier(WebClient.Builder builder) {
        builder.filter(errorHandler());
    }

    public Flux<Optional<Flight>> findFlight(FlightSearchRequest searchRequest) {
        Request request = getConverter().toRequest(searchRequest);
        return callSupplier(request).map(res -> getConverter().toFlight(request, res))
                .filter(flight -> isValidResponse(flight, searchRequest))
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

    private boolean isValidResponse(Flight flight, FlightSearchRequest searchRequest) {
        List<String> errors = new ArrayList<>();
        if (!flight.origin().equalsIgnoreCase(searchRequest.origin())) {
            errors.add("origin is different from requested");
        }
        if (!flight.destination().equalsIgnoreCase(searchRequest.destination())) {
            errors.add("destination is different from requested");
        }
        if (flight.departureDate() == null ||
                !LocalDate.ofInstant(flight.departureDate(), ZoneId.of("UTC")).equals(searchRequest.departureDate())) {
            errors.add("departureDate is different from requested");
        }
        if (flight.returnDate() == null ||
                !LocalDate.ofInstant(flight.returnDate(), ZoneId.of("UTC")).equals(searchRequest.returnDate())) {
            errors.add("returnDate is different from requested");
        }
        if (!Objects.equals(flight.numberOfPassenger(), searchRequest.numberOfPassenger())) {
            errors.add("numberOfPassenger is different from requested");
        }
        if (flight.price() == null || flight.price().compute().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("flight price is lower than 0");
        }
        if (errors.size() > 0) {
            log.warn("Invalid flight detected:\n" + errors + "\nRequest: " + searchRequest + "\nFlight received: " + flight);
            return false;
        }
        return true;
    }
}
