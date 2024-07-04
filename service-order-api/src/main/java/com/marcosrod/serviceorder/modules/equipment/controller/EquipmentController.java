package com.marcosrod.serviceorder.modules.equipment.controller;

import com.marcosrod.serviceorder.modules.equipment.controller.contract.IEquipmentController;
import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentRequest;
import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentResponse;
import com.marcosrod.serviceorder.modules.equipment.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EquipmentController implements IEquipmentController {

    private final EquipmentService service;

    @Override
    public EquipmentResponse save(EquipmentRequest request) {
        return service.save(request);
    }
}
