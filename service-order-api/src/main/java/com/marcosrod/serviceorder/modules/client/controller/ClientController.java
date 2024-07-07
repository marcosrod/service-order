package com.marcosrod.serviceorder.modules.client.controller;

import com.marcosrod.serviceorder.modules.client.controller.contract.IClientController;
import com.marcosrod.serviceorder.modules.client.dto.ClientRequest;
import com.marcosrod.serviceorder.modules.client.dto.ClientResponse;
import com.marcosrod.serviceorder.modules.client.filter.ClientFilter;
import com.marcosrod.serviceorder.modules.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ClientController implements IClientController {

    private final ClientService service;

    @Override
    public ClientResponse save(ClientRequest request) {
        return service.save(request);
    }

    @Override
    public Page<ClientResponse> getAll(Pageable pageable, ClientFilter filter) {
        return service.getAll(pageable, filter);
    }
}
