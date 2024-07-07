package com.marcosrod.serviceorder.modules.client.filter;

import org.junit.jupiter.api.Test;

import static com.marcosrod.serviceorder.modules.client.helper.ClientHelper.getClientAllParametersFilter;
import static com.marcosrod.serviceorder.modules.client.helper.ClientHelper.getClientAllParametersPredicate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientFilterTest {

    @Test
    void toPredicate_shouldReturnClientPredicateWithAllParameters_whenRequested() {
        var resultFilter = getClientAllParametersFilter();
        var expectedPredicate = getClientAllParametersPredicate();

        assertEquals(expectedPredicate.build(),
                resultFilter.toPredicate().build());
    }
}
