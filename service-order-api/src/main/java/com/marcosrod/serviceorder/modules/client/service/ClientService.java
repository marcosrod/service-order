package com.marcosrod.serviceorder.modules.client.service;

import com.marcosrod.serviceorder.modules.client.dto.ClientRequest;
import com.marcosrod.serviceorder.modules.client.dto.ClientResponse;
import com.marcosrod.serviceorder.modules.client.model.Client;

public interface ClientService {
    ClientResponse save(ClientRequest request);
    Client findById(Long id);
}
