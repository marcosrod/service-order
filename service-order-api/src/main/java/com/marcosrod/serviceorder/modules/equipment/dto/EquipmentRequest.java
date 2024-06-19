package com.marcosrod.serviceorder.modules.equipment.dto;

import jakarta.validation.constraints.NotEmpty;

public record EquipmentRequest(@NotEmpty String type, @NotEmpty String model) {
}
