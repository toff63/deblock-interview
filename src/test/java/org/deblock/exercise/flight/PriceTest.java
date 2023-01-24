package org.deblock.exercise.flight;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PriceTest {

    record TestInput(Price price, BigDecimal expected) {
    }

    @DisplayName("Test price computing")
    @ParameterizedTest
    @MethodSource("priceAndExpected")
    void testPriceComputation(TestInput testInput) {
        assertEquals(testInput.expected(), testInput.price().compute());
    }

    static Stream<Arguments> priceAndExpected() {
        return Stream.of(
                arguments(named(
                        "Just base price",
                        new TestInput(Price.of(BigDecimal.valueOf(1234)), BigDecimal.valueOf(1234).setScale(2, RoundingMode.DOWN))
                )),
                arguments(named(
                        "Base price with discount", new TestInput(
                                new Price(BigDecimal.valueOf(1000), Optional.empty(), Optional.of(BigDecimal.valueOf(25))),
                                BigDecimal.valueOf(750).setScale(2, RoundingMode.DOWN)
                        )
                )),
                arguments(named(
                        "Base price with tax", new TestInput(
                                new Price(BigDecimal.valueOf(1000), Optional.of(BigDecimal.valueOf(25)), Optional.empty()),
                                BigDecimal.valueOf(975).setScale(2, RoundingMode.DOWN)
                        )
                )),
                arguments(named(
                        "Base price with tax and discount", new TestInput(
                                new Price(BigDecimal.valueOf(1000), Optional.of(BigDecimal.valueOf(25)), Optional.of(BigDecimal.valueOf(25))),
                                BigDecimal.valueOf(725).setScale(2, RoundingMode.DOWN)
                        )
                ))

        );
    }

}