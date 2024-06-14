package com.marcosrod.serviceorder.modules.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcosrod.serviceorder.modules.order.model.Order;

import java.time.LocalDateTime;

public record OrderReport(Long orderId, String status, @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime creationDate,
                          String equipmentType, String equipmentModel, String equipmentProblem, String clientName,
                          String clientEmail, String clientPhone, Long technicianId, Long receptionistId) {

    public static OrderReport of(Order order) {
        var equipment = order.getEquipment();
        var client = order.getClient();

        return new OrderReport(order.getId(), order.getStatus().getDescription(), order.getCreationDate(),
                equipment.getType(), equipment.getModel(), order.getEquipmentProblem(), client.getName(),
                client.getEmail(), client.getPhone(), order.getTechnicianId(), order.getReceptionistId());
    }
}
