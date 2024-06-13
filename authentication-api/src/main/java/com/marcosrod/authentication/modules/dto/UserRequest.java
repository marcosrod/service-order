package com.marcosrod.authentication.modules.dto;

import com.marcosrod.authentication.modules.enums.Role;

public record UserRequest(String name, String email, String password, Role role) {
}
