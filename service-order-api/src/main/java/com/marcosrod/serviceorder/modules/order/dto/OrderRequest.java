package com.marcosrod.serviceorder.modules.order.dto;

import java.time.LocalDateTime;

public record OrderRequest(Long receptionistId, Long clientId, Long equipmentId, Long technicianId,
                           String equipmentProblem, LocalDateTime startDate,
                           LocalDateTime finishDate, String serviceDetails) {
}
