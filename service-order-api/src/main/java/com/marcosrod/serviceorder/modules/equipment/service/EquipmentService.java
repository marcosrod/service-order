package com.marcosrod.serviceorder.modules.equipment.service;

import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentRequest;
import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentResponse;
import com.marcosrod.serviceorder.modules.equipment.model.Equipment;

public interface EquipmentService {
    EquipmentResponse save(EquipmentRequest request);
    Equipment findById(Long id);
}
