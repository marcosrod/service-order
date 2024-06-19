package com.marcosrod.authentication.modules.user.dto;

import com.marcosrod.authentication.modules.user.enums.Role;

public record UserRequest(String name, String email, String password, Role role) {
}
