package com.marcosrod.serviceorder.modules.client.dto;

import jakarta.validation.constraints.NotEmpty;

public record ClientRequest(@NotEmpty String name, @NotEmpty String address, @NotEmpty String phone,
                            @NotEmpty String email) {
}