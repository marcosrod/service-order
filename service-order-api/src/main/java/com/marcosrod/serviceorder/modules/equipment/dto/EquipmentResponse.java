package com.marcosrod.serviceorder.modules.equipment.dto;

import com.marcosrod.serviceorder.modules.equipment.model.Equipment;

public record EquipmentResponse(Long id, String type, String model) {

    public static EquipmentResponse of(Equipment equipment) {
        return new EquipmentResponse(equipment.getId(), equipment.getType(), equipment.getModel());
    }
}
