package com.marcosrod.serviceorder.modules.order.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    P("Pending"),
    S("Started"),
    PD("Paused"),
    F("Finished");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }
}
