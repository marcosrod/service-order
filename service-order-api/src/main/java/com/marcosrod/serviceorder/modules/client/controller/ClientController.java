package com.marcosrod.serviceorder.modules.client.controller;

import com.marcosrod.serviceorder.modules.client.controller.contract.IClientController;
import com.marcosrod.serviceorder.modules.client.dto.ClientRequest;
import com.marcosrod.serviceorder.modules.client.dto.ClientResponse;
import com.marcosrod.serviceorder.modules.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ClientController implements IClientController {

    private final ClientService service;

    @Override
    public ClientResponse save(ClientRequest request) {
        return service.save(request);
    }
}
