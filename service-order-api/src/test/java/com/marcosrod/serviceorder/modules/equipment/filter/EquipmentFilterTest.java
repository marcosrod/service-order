package com.marcosrod.serviceorder.modules.equipment.filter;

import org.junit.jupiter.api.Test;

import static com.marcosrod.serviceorder.modules.equipment.helper.EquipmentHelper.getEquipmentAllParametersFilter;
import static com.marcosrod.serviceorder.modules.equipment.helper.EquipmentHelper.getEquipmentAllParametersPredicate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentFilterTest {

    @Test
    void toPredicate_shouldReturnEquipmentPredicateWithAllParameters_whenRequested() {
        var resultFilter = getEquipmentAllParametersFilter();
        var expectedPredicate = getEquipmentAllParametersPredicate();

        assertEquals(expectedPredicate.build(),
                resultFilter.toPredicate().build());
    }
}
