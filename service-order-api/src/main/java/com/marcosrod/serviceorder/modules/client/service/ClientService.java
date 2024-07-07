package com.marcosrod.serviceorder.modules.client.service;

import com.marcosrod.serviceorder.modules.client.dto.ClientRequest;
import com.marcosrod.serviceorder.modules.client.dto.ClientResponse;
import com.marcosrod.serviceorder.modules.client.filter.ClientFilter;
import com.marcosrod.serviceorder.modules.client.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    ClientResponse save(ClientRequest request);
    Client findById(Long id);
    Page<ClientResponse> getAll(Pageable pageable, ClientFilter filter);
}
