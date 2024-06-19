package com.marcosrod.authentication.modules.common.enums;

import lombok.Getter;

@Getter
public enum ValidationError {

    USER_EMAIL_ALREADY_EXISTS("This email is already registered."),
    USER_EMAIL_NOT_FOUND("This user email doesn't exists"),
    USER_EMAIL_PASSWORD_NOT_FOUND("Invalid user email or password.");

    private final String message;

    ValidationError(String message) {
        this.message = message;
    }
}
