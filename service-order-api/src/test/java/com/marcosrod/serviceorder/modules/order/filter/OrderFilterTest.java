package com.marcosrod.serviceorder.modules.order.filter;

import org.junit.jupiter.api.Test;

import static com.marcosrod.serviceorder.modules.order.helper.OrderHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderFilterTest {

    @Test
    void toPredicate_shouldReturnOrderPredicateWithAllParameters_whenRequested() {
        var resultFilter = getOrderAllParametersFilter();
        var expectedPredicate = getOrderAllParametersPredicate();

        assertEquals(expectedPredicate.build(),
                resultFilter.toPredicate().build());
    }
}
