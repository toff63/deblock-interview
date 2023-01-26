package org.deblock.exercise.flightsuppliers.crazyair;

import org.deblock.exercise.flight.CabinClass;
import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.flight.Price;
import org.deblock.exercise.flight.SearchProvider;
import org.deblock.exercise.flightsuppliers.FlightSupplierResponseConverter;
import org.deblock.exercise.search.FlightSearchRequest;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

public class CrazyAirConverter implements FlightSupplierResponseConverter<CrazyAirRequest, CrazyAirResponse> {

    private final Map<CrazyAirCabinClass, CabinClass> cabinClassMapping = Map.of(CrazyAirCabinClass.E, CabinClass.Economy, CrazyAirCabinClass.B, CabinClass.Business);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    protected URI toURI(UriBuilder uriBuilder, CrazyAirRequest crazyAirRequest) {
        return uriBuilder.queryParam("origin", crazyAirRequest.origin())
                .queryParam("destination", crazyAirRequest.destination())
                .queryParam("departureDate", dateTimeFormatter.format(crazyAirRequest.departureDate()))
                .queryParam("returnDate", dateTimeFormatter.format(crazyAirRequest.returnDate()))
                .queryParam("passengerCount", crazyAirRequest.passengerCount())
                .build();
    }

    @Override
    public CrazyAirRequest toRequest(FlightSearchRequest request) {
        return new CrazyAirRequest(request.origin(), request.destination(), request.departureDate(), request.returnDate(), request.numberOfPassenger());
    }

    public Flight toFlight(CrazyAirRequest req, CrazyAirResponse res) {
        return new Flight(
                res.departureAirportCode(),
                res.destinationAirportCode(),
                res.departureDate().toInstant(ZoneOffset.UTC),
                res.arrivalDate().toInstant(ZoneOffset.UTC),
                req.passengerCount(),
                res.airline(),
                SearchProvider.CrazyAir,
                Optional.ofNullable(cabinClassMapping.get(res.cabinclass())),
                Price.of(res.price())
        );
    }
}
