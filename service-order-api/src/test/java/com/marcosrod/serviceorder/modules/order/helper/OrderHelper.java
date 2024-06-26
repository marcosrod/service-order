package com.marcosrod.serviceorder.modules.order.helper;

import com.marcosrod.serviceorder.modules.order.dto.*;
import com.marcosrod.serviceorder.modules.order.filter.OrderFilter;
import com.marcosrod.serviceorder.modules.order.filter.OrderPredicate;
import com.marcosrod.serviceorder.modules.order.model.Order;
import com.marcosrod.serviceorder.modules.order.model.OrderTracking;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.marcosrod.serviceorder.modules.client.helper.ClientHelper.getSavedClient;
import static com.marcosrod.serviceorder.modules.common.helper.ConstantUtil.ID_ATTRIBUTE;
import static com.marcosrod.serviceorder.modules.common.helper.ConstantUtil.TEST_ID_ONE;
import static com.marcosrod.serviceorder.modules.equipment.helper.EquipmentHelper.getSavedEquipment;
import static com.marcosrod.serviceorder.modules.order.enums.OrderStatus.P;

public class OrderHelper {

    public static final Long TEST_ID_TWO = 2L;
    private static final String TEST_PROGRESS_DETAILS = "Test progress details";
    private static final String TEST_EQUIPMENT_PROBLEM = "Test Equipment Problem";
    private static final LocalDateTime TESTE_DATE = LocalDateTime.of(2100, 1, 1, 8,
                    0, 0).withNano(0);

    public static OrderRequest getOrderRequest() {
        return new OrderRequest(TEST_ID_ONE, TEST_ID_ONE, TEST_ID_ONE, TEST_ID_TWO, TEST_EQUIPMENT_PROBLEM);
    }

    public static Order getOrder() {
        return new Order(TEST_ID_ONE, TEST_ID_TWO, getSavedClient(), getSavedEquipment(),
                TEST_EQUIPMENT_PROBLEM, TESTE_DATE, P);
    }

    public static Order getSavedOrder() {
        return new Order(TEST_ID_ONE, TEST_ID_ONE, TEST_ID_TWO, getSavedClient(), getSavedEquipment(),
                TEST_EQUIPMENT_PROBLEM, TESTE_DATE, P);
    }

    public static OrderResponse getOrderResponse() {
        return OrderResponse.of(getSavedOrder());
    }

    public static OrderProgressRequest getProgressRequest() {
        return new OrderProgressRequest(TEST_ID_ONE, P, TEST_PROGRESS_DETAILS);
    }

    public static OrderTracking getOrderTracking() {
        return new OrderTracking(getSavedOrder(), P, TEST_PROGRESS_DETAILS, TESTE_DATE);
    }

    public static OrderTracking getSavedOrderTracking() {
        return new OrderTracking(TEST_ID_ONE, getSavedOrder(), P, TEST_PROGRESS_DETAILS, TESTE_DATE);
    }

    public static OrderTrackingResponse getOrderTrackingResponse() {
        return OrderTrackingResponse.of(getSavedOrderTracking());
    }

    public static OrderReportResponse getOrderReportResponse() {
        return OrderReportResponse.of(getSavedOrder());
    }

    public static OrderFilter getOrderFilter() {
        var filter = new OrderFilter();
        filter.setId(TEST_ID_ONE);

        return filter;
    }

    public static OrderFilter getOrderAllParametersFilter() {
        var equipment = getSavedEquipment();
        var client = getSavedClient();

        return new OrderFilter(TEST_ID_ONE, P, equipment.getType(), equipment.getModel(), TEST_EQUIPMENT_PROBLEM,
                client.getName(), client.getEmail(), TEST_ID_ONE, TEST_ID_ONE);
    }

    public static OrderPredicate getOrderAllParametersPredicate() {
        var equipment = getSavedEquipment();
        var client = getSavedClient();

        return new OrderPredicate()
                .withId(TEST_ID_ONE)
                .withStatus(P)
                .withEquipmentType(equipment.getType())
                .withEquipmentModel(equipment.getModel())
                .withEquipmentProblem(TEST_EQUIPMENT_PROBLEM)
                .withClientName(client.getName())
                .withClientEmail(client.getEmail())
                .withTechnicianId(TEST_ID_ONE)
                .withReceptionistId(TEST_ID_ONE);
    }

    public static Page<OrderResponse> getOrderResponsePage() {
        return new PageImpl<>(Collections.singletonList(getOrderResponse()),
                getOrderPageable(), TEST_ID_ONE);
    }

    public static Page<OrderTrackingResponse> getOrderTrackingResponsePage() {
        return new PageImpl<>(Collections.singletonList(getOrderTrackingResponse()),
                getOrderPageable(), TEST_ID_ONE);
    }

    public static Page<OrderReportResponse> getOrderReportResponsePage() {
        return new PageImpl<>(Collections.singletonList(getOrderReportResponse()),
                getOrderPageable(), TEST_ID_ONE);
    }

    public static Pageable getOrderPageable() {
        return PageRequest.of(0, 10, Sort.by(ID_ATTRIBUTE));
    }
}
