package com.marcosrod.serviceorder.modules.order.enums;

public enum OrderStatus {

    P("Pending"),
    S("Started"),
    PD("Paused"),
    F("Finished");

    public String getDescription() {
        return description;
    }

    OrderStatus(String description) {
        this.description = description;
    }

    private String description;
}
