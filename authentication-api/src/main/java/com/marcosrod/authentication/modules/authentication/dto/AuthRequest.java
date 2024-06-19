package com.marcosrod.authentication.modules.authentication.dto;

import jakarta.validation.constraints.NotEmpty;

public record AuthRequest(@NotEmpty String email, @NotEmpty String password) {
}
