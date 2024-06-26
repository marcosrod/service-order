package com.marcosrod.serviceorder.modules.client.service;

import com.marcosrod.serviceorder.modules.common.enums.ValidationError;
import com.marcosrod.serviceorder.modules.client.dto.ClientRequest;
import com.marcosrod.serviceorder.modules.client.dto.ClientResponse;
import com.marcosrod.serviceorder.modules.client.model.Client;
import com.marcosrod.serviceorder.modules.client.repository.ClientRepository;
import com.marcosrod.serviceorder.modules.common.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    public ClientResponse save(ClientRequest request) {
        validateDuplicatedClient(request.email(), request.phone());
        var clientToSave = new Client(request.name(), request.address(), request.phone(), request.email());
        var savedClient = repository.save(clientToSave);

        return new ClientResponse(savedClient.getId(), savedClient.getName(), savedClient.getAddress(),
                savedClient.getPhone(), savedClient.getEmail());

    }

    private void validateDuplicatedClient(String email, String phone) {
        if (repository.existsByEmail(email)) {
            throw new ValidationException(ValidationError.CLIENT_EMAIL_ALREADY_EXISTS.getMessage());
        } else if (repository.existsByPhone(phone)) {
            throw new ValidationException(ValidationError.CLIENT_PHONE_ALREADY_EXISTS.getMessage());
        }
    }
    public Client findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException(ValidationError.CLIENT_NOT_FOUND.getMessage()));
    }
}
