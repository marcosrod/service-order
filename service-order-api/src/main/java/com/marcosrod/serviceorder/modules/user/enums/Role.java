package com.marcosrod.serviceorder.modules.user.enums;

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
