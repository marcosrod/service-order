package com.marcosrod.serviceorder.common.enums;

import lombok.Getter;

@Getter
public enum ValidationError {

    ORDER_ALREADY_EXISTS("There's already an open Order for this same Client and Equipment."),
    ORDER_NOT_FOUND("This order doesn't exists."),
    USER_NOT_FOUND("One or more requested users doesn't exists."),
    CLIENT_EMAIL_ALREADY_EXISTS("This email is already registered."),
    CLIENT_PHONE_ALREADY_EXISTS("This phone is already registered."),
    CLIENT_NOT_FOUND("This client doesn't exists"),
    EQUIPMENT_ALREADY_EXISTS("This equipment is already registered."),
    EQUIPMENT_NOT_FOUND("This equipment doesn't exists.");

    private final String message;

    ValidationError(String message) {
        this.message = message;
    }
}
