package com.marcosrod.authentication.modules.user.enums;

import lombok.Getter;

@Getter
public enum Role {
    R("Receptionist"),
    T("Technician");

    private final String description;

    public String getAuthority() {
        return "ROLE_" + description;
    }


    Role(String description) {
        this.description = description;
    }
}
