package com.marcosrod.serviceorder.modules.equipment.dto;

import org.junit.jupiter.api.Test;

import static com.marcosrod.serviceorder.modules.equipment.helper.EquipmentHelper.getEquipmentResponse;
import static com.marcosrod.serviceorder.modules.equipment.helper.EquipmentHelper.getSavedEquipment;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentResponseTest {

    @Test
    void of_shouldReturnEquipmentResponse_whenRequested() {
        var expectedResponse = getEquipmentResponse();

        assertEquals(expectedResponse, EquipmentResponse.of(getSavedEquipment()));
    }
}
