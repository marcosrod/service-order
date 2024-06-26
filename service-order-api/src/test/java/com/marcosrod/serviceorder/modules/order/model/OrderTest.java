package com.marcosrod.serviceorder.modules.order.model;

import com.marcosrod.serviceorder.modules.order.enums.OrderStatus;
import org.junit.jupiter.api.Test;

import static com.marcosrod.serviceorder.modules.order.enums.OrderStatus.S;
import static com.marcosrod.serviceorder.modules.order.helper.OrderHelper.getSavedOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OrderTest {

    @Test
    void updateOrderStatus_shouldUpdateStatus_whenRequested() {
        var order = getSavedOrder();
        var orderPreviousStatus = order.getStatus();
        order.updateOrderStatus(S);

        assertNotEquals(orderPreviousStatus, order.getStatus());
        assertEquals(order.getStatus(), S);
    }
}
