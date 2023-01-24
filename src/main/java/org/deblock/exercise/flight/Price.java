package org.deblock.exercise.flight;

import java.math.BigDecimal;
import java.util.Optional;

public record Price(
        BigDecimal basePrice,
        Optional<BigDecimal> tax,
        Optional<BigDecimal> discount) {

    public static Price of(BigDecimal basePRice) {
        return new Price(basePRice, Optional.empty(), Optional.empty());
    }

    public BigDecimal compute() {
        BigDecimal finalTax = tax.orElse(new BigDecimal(0));
        BigDecimal finalDiscountPercent = discount.orElse(BigDecimal.ZERO).movePointLeft(2);
        return basePrice.multiply(BigDecimal.ONE.subtract(finalDiscountPercent)).subtract(finalTax);
    }
}
