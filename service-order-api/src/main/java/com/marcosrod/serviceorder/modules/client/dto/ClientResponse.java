package com.marcosrod.serviceorder.modules.client.dto;

import com.marcosrod.serviceorder.modules.client.model.Client;

public record ClientResponse(Long id, String name, String address, String phone, String email) {

    public static ClientResponse of(Client client) {
        return new ClientResponse(client.getId(), client.getName(), client.getAddress(), client.getPhone(),
                client.getEmail());
    }
}
