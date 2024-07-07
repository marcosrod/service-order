package com.marcosrod.serviceorder.modules.equipment.helper;

import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentRequest;
import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentResponse;
import com.marcosrod.serviceorder.modules.equipment.filter.EquipmentFilter;
import com.marcosrod.serviceorder.modules.equipment.filter.EquipmentPredicate;
import com.marcosrod.serviceorder.modules.equipment.model.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;

import static com.marcosrod.serviceorder.modules.common.helper.ConstantUtil.TEST_ID_ONE;
import static com.marcosrod.serviceorder.modules.order.helper.OrderHelper.getPageable;

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

    public static Page<EquipmentResponse> getEquipmentResponsePage() {
        return new PageImpl<>(Collections.singletonList(getEquipmentResponse()), getPageable(), TEST_ID_ONE);
    }

    public static EquipmentFilter getEquipmentFilter() {
        var filter = new EquipmentFilter();
        filter.setId(TEST_ID_ONE);

        return filter;
    }

    public static EquipmentFilter getEquipmentAllParametersFilter() {
        var savedEquipment = getSavedEquipment();

        return new EquipmentFilter(savedEquipment.getId(), savedEquipment.getType(), savedEquipment.getModel());
    }

    public static EquipmentPredicate getEquipmentAllParametersPredicate() {
        var savedEquipment = getSavedEquipment();

        return new EquipmentPredicate()
                .withId(savedEquipment.getId())
                .withType(savedEquipment.getType())
                .withModel(savedEquipment.getModel());
    }
}
