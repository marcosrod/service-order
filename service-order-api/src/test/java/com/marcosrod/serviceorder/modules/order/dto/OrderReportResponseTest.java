package com.marcosrod.serviceorder.modules.order.dto;

import org.junit.jupiter.api.Test;

import static com.marcosrod.serviceorder.modules.order.helper.OrderHelper.getSavedOrder;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderReportResponseTest {

    @Test
    void of_shouldReturnOrderReportResponse_whenRequested() {
        var savedOrder = getSavedOrder();
        var client = savedOrder.getClient();
        var equipment = savedOrder.getEquipment();
        var resultResponse = OrderReportResponse.of(savedOrder);

        assertThat(resultResponse)
                .extracting(OrderReportResponse::orderId, OrderReportResponse::status, OrderReportResponse::creationDate,
                        OrderReportResponse::equipmentType, OrderReportResponse::equipmentModel,
                        OrderReportResponse::equipmentProblem, OrderReportResponse::clientName,
                        OrderReportResponse::clientEmail, OrderReportResponse::clientPhone, OrderReportResponse::technicianId,
                        OrderReportResponse::receptionistId)
                .containsExactly(savedOrder.getId(), savedOrder.getStatus().getDescription(), savedOrder.getCreationDate(),
                        equipment.getType(), equipment.getModel(), savedOrder.getEquipmentProblem(), client.getName(),
                        client.getEmail(), client.getPhone(), savedOrder.getTechnicianId(), savedOrder.getReceptionistId());
    }
}
