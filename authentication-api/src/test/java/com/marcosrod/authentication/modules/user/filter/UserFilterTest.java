package com.marcosrod.authentication.modules.user.filter;

import org.junit.jupiter.api.Test;

import static com.marcosrod.authentication.modules.user.helper.UserHelper.getUserAllParametersFilter;
import static com.marcosrod.authentication.modules.user.helper.UserHelper.getUserAllParametersPredicate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserFilterTest {

    @Test
    void toPredicate_shouldReturnUserPredicateWithAllParameters_whenRequested() {
        var resultFilter = getUserAllParametersFilter();
        var expectedPredicate = getUserAllParametersPredicate();

        assertEquals(expectedPredicate.build(),
                resultFilter.toPredicate().build());
    }
}
