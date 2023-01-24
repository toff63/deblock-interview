package org.deblock.exercise.api.v1;

import org.deblock.exercise.api.v1.contract.FlightSearchRequest;
import org.deblock.exercise.api.v1.contract.FlightSearchResult;
import org.deblock.exercise.api.v1.contract.Supplier;
import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.flight.SearchProvider;
import org.deblock.exercise.search.FlightSearchService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.inject.Inject;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class FlightSearchHandler {
    @Inject
    FlightSearchService service;

    final private Map<SearchProvider, Supplier> searchProviderConverter = Map.of(SearchProvider.ToughJet, Supplier.ToughJet, SearchProvider.CrazyAir, Supplier.CrazyAir);

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

    public ServerResponse searchFlight(ServerRequest request) throws ServletException, IOException {
        FlightSearchRequest searchRequest = request.body(FlightSearchRequest.class);
        List<Flight> flights = service.search(searchRequest.origin(), searchRequest.destination(), searchRequest.departureDate(), searchRequest.returnDate(), searchRequest.numberOfPassenger());
        List<FlightSearchResult> result = flights.stream().map(this::convert).toList();
        return ServerResponse.ok().body(result);
    }
}
