package com.marcosrod.serviceorder.modules.client.service;

import com.marcosrod.serviceorder.modules.client.dto.ClientRequest;
import com.marcosrod.serviceorder.modules.client.dto.ClientResponse;
import com.marcosrod.serviceorder.modules.client.model.Client;
import com.marcosrod.serviceorder.modules.client.repository.ClientRepository;
import com.marcosrod.serviceorder.common.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientResponse save(ClientRequest request) {
        validateDuplicatedClient(request.email(), request.phone());

        var savedClient = repository.save(Client.of(request));

        return ClientResponse.of(savedClient);

    }

    private void validateDuplicatedClient(String email, String phone) {
        if (repository.existsByEmail(email)) {
            throw new ValidationException("This email is already registered.");
        } else if (repository.existsByPhone(phone)) {
            throw new ValidationException("This phone is already registered.");
        }
    }
    public Client findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException("This client doesn't exists"));
    }
}
