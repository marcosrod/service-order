package com.marcosrod.serviceorder.modules.equipment.controller.contract;

import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentRequest;
import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentResponse;
import com.marcosrod.serviceorder.modules.equipment.filter.EquipmentFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/equipments")
public interface IEquipmentController {

    @Operation(summary = "Save new Equipment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New Equipment saved."),
            @ApiResponse(responseCode = "400", description = "Invalid request.", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "User hasn't the required permission.", content = @Content)
    })
    @PostMapping
    EquipmentResponse save(@RequestBody @Valid EquipmentRequest request);

    @Operation(summary = "Find All Equipments.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns Equipment Page"),
            @ApiResponse(responseCode = "200", description = "Returns Empty Page"),
            @ApiResponse(responseCode = "401", description = "User not logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "User hasn't the required permission.", content = @Content)
    })
    @GetMapping
    Page<EquipmentResponse> getAll(Pageable pageable, EquipmentFilter filter);
}
