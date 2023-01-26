package org.deblock.exercise.flightsuppliers.toughjet;

import org.deblock.exercise.flightsuppliers.SupplierRequest;

import java.time.LocalDate;

public record ToughJetRequest(
        String from,
        String to,
        LocalDate outboundDate,
        LocalDate inboundDate,
        Short numberOfAdults
) implements SupplierRequest {
}
