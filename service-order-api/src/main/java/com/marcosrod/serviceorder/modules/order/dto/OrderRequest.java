package com.marcosrod.serviceorder.modules.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(@NotNull Long receptionistId, @NotNull Long clientId, @NotNull Long equipmentId,
                           @NotNull Long technicianId, @NotEmpty String equipmentProblem) {
}
