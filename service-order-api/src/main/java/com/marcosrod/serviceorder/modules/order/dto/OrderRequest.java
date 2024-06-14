package com.marcosrod.serviceorder.modules.order.dto;

public record OrderRequest(Long receptionistId, Long clientId, Long equipmentId, Long technicianId,
                           String equipmentProblem) {
}
