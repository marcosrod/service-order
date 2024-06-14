package com.marcosrod.serviceorder.modules.order.dto;

import com.marcosrod.serviceorder.modules.order.enums.OrderStatus;

public record OrderProgressRequest(Long orderId, OrderStatus status, String progressDetails) {
}
