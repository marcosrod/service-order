package com.marcosrod.serviceorder.modules.equipment.service.impl;

import com.marcosrod.serviceorder.modules.common.enums.ValidationError;
import com.marcosrod.serviceorder.modules.common.exception.ValidationException;
import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentRequest;
import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentResponse;
import com.marcosrod.serviceorder.modules.equipment.filter.EquipmentFilter;
import com.marcosrod.serviceorder.modules.equipment.model.Equipment;
import com.marcosrod.serviceorder.modules.equipment.repository.EquipmentRepository;
import com.marcosrod.serviceorder.modules.equipment.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository repository;

    @Override
    public Page<EquipmentResponse> getAll(Pageable pageable, EquipmentFilter filter) {
        return repository.findAll(filter.toPredicate().build(), pageable)
                .map(EquipmentResponse::of);
    }

    public EquipmentResponse save(EquipmentRequest request) {
        validateDuplicatedEquipment(request.type(), request.model());
        var equipmentToSave = new Equipment(request.type(), request.model());
        var savedEquipment = repository.save(equipmentToSave);

        return new EquipmentResponse(savedEquipment.getId(), savedEquipment.getType(), savedEquipment.getModel());
    }

    private void validateDuplicatedEquipment(String type, String model) {
        if (repository.existsByTypeAndModel(type, model)) {
            throw new ValidationException(ValidationError.EQUIPMENT_ALREADY_EXISTS.getMessage());
        }
    }

    public Equipment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException(ValidationError.EQUIPMENT_NOT_FOUND.getMessage()));
    }
}
