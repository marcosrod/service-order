package com.marcosrod.serviceorder.modules.equipment.service.impl;

import com.marcosrod.serviceorder.common.exception.ValidationException;
import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentRequest;
import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentResponse;
import com.marcosrod.serviceorder.modules.equipment.model.Equipment;
import com.marcosrod.serviceorder.modules.equipment.repository.EquipmentRepository;
import com.marcosrod.serviceorder.modules.equipment.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository repository;

    public EquipmentResponse save(EquipmentRequest request) {
        validateDuplicatedEquipment(request.type(), request.model());

        var savedEquipment = repository.save(Equipment.of(request));

        return EquipmentResponse.of(savedEquipment);
    }

    private void validateDuplicatedEquipment(String type, String model) {
        if (repository.existsByTypeAndModel(type, model)) {
            throw new ValidationException("This equipment is already registered.");
        }
    }

    public Equipment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException("This equipment doesn't exists."));
    }

}
