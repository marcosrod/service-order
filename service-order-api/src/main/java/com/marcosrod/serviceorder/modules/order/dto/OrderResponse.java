package com.marcosrod.serviceorder.modules.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcosrod.serviceorder.modules.order.model.Order;

import java.time.LocalDateTime;

public record OrderResponse(Long id, Long receptionistId, Long clientId, String clientName, Long equipmentId,
                            Long technicianId, String equipmentProblem,
                            @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime creationDate,
                            LocalDateTime startDate, LocalDateTime finishDate, String status,
                            String serviceDetails){

    public static OrderResponse of(Order order) {
        var client = order.getClient();

        return new OrderResponse(order.getId(), order.getReceptionist(), client.getId(), client.getName(),
                order.getEquipment().getId(), order.getTechnicianId(), order.getEquipmentProblem(),
                order.getCreationDate(), order.getStartDate(), order.getFinishDate(),
                order.getStatus().getDescription(), order.getServiceDetails());
    }
}
