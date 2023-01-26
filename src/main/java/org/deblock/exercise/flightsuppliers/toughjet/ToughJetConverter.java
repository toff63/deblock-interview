package org.deblock.exercise.flightsuppliers.toughjet;

import org.deblock.exercise.flight.Flight;
import org.deblock.exercise.flight.Price;
import org.deblock.exercise.flight.SearchProvider;
import org.deblock.exercise.flightsuppliers.FlightSupplierResponseConverter;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ToughJetConverter implements FlightSupplierResponseConverter<ToughJetRequest, ToughJetResponse> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    protected URI toURI(UriBuilder uriBuilder, ToughJetRequest request) {
        return uriBuilder.queryParam("from", request.from())
                .queryParam("to", request.to())
                .queryParam("outboundDate", formatter.format(request.outboundDate()))
                .queryParam("inboundDate", formatter.format(request.inboundDate()))
                .queryParam("numberOfAdults", request.numberOfAdults())
                .build();
    }

    @Override
    public ToughJetRequest toRequest(String origin, String destination, LocalDate departureDate, LocalDate returnDate, Short numberOfPassenger) {
        return new ToughJetRequest(origin, destination, departureDate, returnDate, numberOfPassenger);
    }

    @Override
    public Flight toFlight(ToughJetRequest req, ToughJetResponse res) {
        return new Flight(
                res.departureAirportName(),
                res.arrivalAirportName(),
                res.outboundDateTime(),
                res.inboundDateTime(),
                req.numberOfAdults(),
                res.carrier(),
                SearchProvider.ToughJet,
                Optional.empty(),
                new Price(res.basePrice(), Optional.ofNullable(res.tax()), Optional.ofNullable(res.discount()))
        );
    }
}
