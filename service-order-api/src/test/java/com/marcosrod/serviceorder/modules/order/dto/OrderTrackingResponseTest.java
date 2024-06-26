package com.marcosrod.serviceorder.modules.order.dto;

import org.junit.jupiter.api.Test;

import static com.marcosrod.serviceorder.modules.order.helper.OrderHelper.getSavedOrderTracking;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderTrackingResponseTest {

    @Test
    void of_shouldReturnOrderTrackingResponse_whenRequested() {
        var savedOrderTracking = getSavedOrderTracking();
        var resultResponse = OrderTrackingResponse.of(savedOrderTracking);

        assertThat(resultResponse)
                .extracting(OrderTrackingResponse::id, OrderTrackingResponse::orderId, OrderTrackingResponse::status,
                        OrderTrackingResponse::progressDetails, OrderTrackingResponse::progressDate)
                .containsExactly(savedOrderTracking.getId(), savedOrderTracking.getOrder().getId(),
                        savedOrderTracking.getStatus().getDescription(), savedOrderTracking.getProgressDetails(),
                        savedOrderTracking.getProgressDate());
    }
}
