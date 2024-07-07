package com.marcosrod.authentication.modules.user.dto;

import com.marcosrod.authentication.modules.user.model.User;

public record UserResponse(Long id, String name, String email, String role) {

    public static UserResponse of(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole().getDescription());
    }
}
