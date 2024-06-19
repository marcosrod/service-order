package com.marcosrod.serviceorder.modules.order.dto;

import com.marcosrod.serviceorder.modules.order.enums.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OrderProgressRequest(@NotNull Long orderId, @NotNull OrderStatus status, @NotEmpty String progressDetails) {
}
