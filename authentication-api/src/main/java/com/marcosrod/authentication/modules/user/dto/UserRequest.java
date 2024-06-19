package com.marcosrod.authentication.modules.user.dto;

import com.marcosrod.authentication.modules.user.enums.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserRequest(@NotEmpty String name, @NotEmpty String email, @NotEmpty String password, @NotNull Role role) {
}
