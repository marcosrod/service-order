package com.marcosrod.serviceorder.modules.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcosrod.serviceorder.modules.order.model.OrderTracking;

import java.time.LocalDateTime;

public record OrderTrackingResponse(Long id, Long orderId, String status, String progressDetails,
                                    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime progressDate) {

    public static OrderTrackingResponse of(OrderTracking orderTracking) {
        return new OrderTrackingResponse(orderTracking.getId(), orderTracking.getOrder().getId(),
                orderTracking.getStatus().getDescription(), orderTracking.getProgressDetails(),
                orderTracking.getProgressDate());
    }
}
