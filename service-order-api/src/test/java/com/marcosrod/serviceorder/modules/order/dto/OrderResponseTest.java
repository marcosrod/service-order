package com.marcosrod.serviceorder.modules.order.dto;

import org.junit.jupiter.api.Test;

import static com.marcosrod.serviceorder.modules.order.helper.OrderHelper.getSavedOrder;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderResponseTest {

    @Test
    void of_shouldReturnOrderResponse_whenRequested() {
        var savedOrder = getSavedOrder();
        var client = savedOrder.getClient();

        var resultResponse = OrderResponse.of(savedOrder);

        assertThat(resultResponse)
                .extracting(OrderResponse::id, OrderResponse::receptionistId, OrderResponse::clientId,
                        OrderResponse::clientName, OrderResponse::equipmentId, OrderResponse::technicianId,
                        OrderResponse::equipmentProblem, OrderResponse::creationDate, OrderResponse::status)
                .containsExactly(savedOrder.getId(), savedOrder.getReceptionistId(), client.getId(), client.getName(),
                        savedOrder.getEquipment().getId(), savedOrder.getTechnicianId(), savedOrder.getEquipmentProblem(),
                        savedOrder.getCreationDate(), savedOrder.getStatus().getDescription());
    }
}
