package com.marcosrod.serviceorder.modules.equipment.controller;

import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentRequest;
import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentResponse;
import com.marcosrod.serviceorder.modules.equipment.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/equipments")
public class EquipmentController {

    private final EquipmentService service;

    @PostMapping
    public EquipmentResponse save(@RequestBody EquipmentRequest request) {
        return service.save(request);
    }
}
