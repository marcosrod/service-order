package com.marcosrod.authentication.modules.enums;

public enum Role {
    R("Receptionist"),
    T("Technician");

    private String description;

    public String getDescription() {
        return description;
    }

    public String getAuthority() {
        return "ROLE_" + description;
    }


    Role(String description) {
        this.description = description;
    }
}
