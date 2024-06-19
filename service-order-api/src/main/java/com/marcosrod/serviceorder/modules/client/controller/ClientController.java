package com.marcosrod.serviceorder.modules.client.controller;

import com.marcosrod.serviceorder.modules.client.dto.ClientRequest;
import com.marcosrod.serviceorder.modules.client.dto.ClientResponse;
import com.marcosrod.serviceorder.modules.client.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService service;

    @PostMapping
    public ClientResponse save(@RequestBody @Valid ClientRequest request) {
        return service.save(request);
    }
}
