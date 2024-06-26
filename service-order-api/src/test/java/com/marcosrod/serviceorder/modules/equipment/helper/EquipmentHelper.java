package com.marcosrod.serviceorder.modules.equipment.helper;

import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentRequest;
import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentResponse;
import com.marcosrod.serviceorder.modules.equipment.model.Equipment;

import static com.marcosrod.serviceorder.modules.common.helper.ConstantUtil.TEST_ID_ONE;

public class EquipmentHelper {

    private static final String TEST_TYPE = "printer";
    private static final String TEST_MODEL = "HP";

    public static EquipmentRequest getEquipmentRequest() {
        return new EquipmentRequest(TEST_TYPE, TEST_MODEL);
    }

    public static Equipment getEquipment() {
        return new Equipment(TEST_TYPE, TEST_MODEL);
    }

    public static Equipment getSavedEquipment() {
        return new Equipment(TEST_ID_ONE, TEST_TYPE, TEST_MODEL);
    }

    public static EquipmentResponse getEquipmentResponse() {
        return new EquipmentResponse(TEST_ID_ONE, TEST_TYPE, TEST_MODEL);
    }
}
