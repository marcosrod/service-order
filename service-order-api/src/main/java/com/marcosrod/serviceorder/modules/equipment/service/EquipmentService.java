package com.marcosrod.serviceorder.modules.equipment.service;

import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentRequest;
import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentResponse;
import com.marcosrod.serviceorder.modules.equipment.filter.EquipmentFilter;
import com.marcosrod.serviceorder.modules.equipment.model.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EquipmentService {
    EquipmentResponse save(EquipmentRequest request);
    Equipment findById(Long id);
    Page<EquipmentResponse> getAll(Pageable pageable, EquipmentFilter filter);
}
