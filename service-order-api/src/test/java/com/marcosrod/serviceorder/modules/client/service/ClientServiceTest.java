package com.marcosrod.serviceorder.modules.client.service;

import com.marcosrod.serviceorder.modules.client.model.Client;
import com.marcosrod.serviceorder.modules.common.enums.ValidationError;
import com.marcosrod.serviceorder.modules.common.exception.ValidationException;
import com.marcosrod.serviceorder.modules.client.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.marcosrod.serviceorder.modules.client.helper.ClientHelper.*;
import static com.marcosrod.serviceorder.modules.common.helper.ConstantUtil.TEST_ID_ONE;
import static com.marcosrod.serviceorder.modules.order.helper.OrderHelper.getPageable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientServiceImpl service;
    @Mock
    private ClientRepository repository;

    @Test
    void getAll_shouldReturnPageClientResponse_whenRequested() {
        var savedClient = getSavedClient();
        var pageable = getPageable();
        var filter = getClientFilter();
        var predicate = filter.toPredicate().build();
        Page<Client> clientsPage = new PageImpl<>(Collections.singletonList(savedClient), pageable, TEST_ID_ONE);

        doReturn(clientsPage).when(repository).findAll(predicate, pageable);

        assertThat(service.getAll(pageable, filter))
                .isEqualTo(getClientResponsePage());

        verify(repository).findAll(predicate, pageable);
    }

    @Test
    void getAll_shouldReturnEmptyPage_whenNoClientsFound() {
        var pageable = getPageable();
        var filter = getClientFilter();
        var predicate = filter.toPredicate().build();
        Page<Client> emptyPage = new PageImpl<>(List.of(), pageable, TEST_ID_ONE);

        doReturn(emptyPage).when(repository).findAll(predicate, pageable);

        assertThat(service.getAll(pageable, filter))
                .isEmpty();

        verify(repository).findAll(predicate, pageable);
    }

    @Test
    void save_shouldReturnClientResponse_whenValidRequest() {
        var clientRequest = getClientRequest();
        var clientEmail = clientRequest.email();
        var clientPhone = clientRequest.phone();
        var clientToSave = getClient();

        doReturn(false).when(repository).existsByEmail(clientEmail);
        doReturn(false).when(repository).existsByPhone(clientPhone);
        doReturn(getSavedClient()).when(repository).save(clientToSave);

        assertThat(service.save(clientRequest))
                .isEqualTo(getClientResponse());

        verify(repository).existsByEmail(clientEmail);
        verify(repository).existsByPhone(clientPhone);
        verify(repository).save(clientToSave);
    }

    @Test
    void save_shouldThrowValidationException_whenDuplicatedEmail() {
        var clientRequest = getClientRequest();
        var clientEmail = clientRequest.email();

        doReturn(true).when(repository).existsByEmail(clientEmail);

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> service.save(clientRequest))
                .withMessage(ValidationError.CLIENT_EMAIL_ALREADY_EXISTS.getMessage());

        verify(repository).existsByEmail(clientEmail);
        verify(repository, never()).existsByPhone(anyString());
        verify(repository, never()).save(any());
    }

    @Test
    void save_shouldThrowValidationException_whenDuplicatedPhone() {
        var clientRequest = getClientRequest();
        var clientEmail = clientRequest.email();
        var clientPhone = clientRequest.phone();

        doReturn(false).when(repository).existsByEmail(clientEmail);
        doReturn(true).when(repository).existsByPhone(clientPhone);


        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> service.save(clientRequest))
                .withMessage(ValidationError.CLIENT_PHONE_ALREADY_EXISTS.getMessage());

        verify(repository).existsByEmail(clientEmail);
        verify(repository).existsByPhone(clientPhone);
        verify(repository, never()).save(any());
    }

    @Test
    void findById_shouldReturnClient_whenClientIdExists() {
        var savedClient = getSavedClient();

        doReturn(Optional.of(savedClient)).when(repository).findById(TEST_ID_ONE);

        assertThat(service.findById(TEST_ID_ONE))
                .isEqualTo(savedClient);

        verify(repository).findById(TEST_ID_ONE);
    }

    @Test
    void findById_shouldThrowValidationException_whenClientIdDoesNotExists() {
        doReturn(Optional.empty()).when(repository).findById(TEST_ID_ONE);

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> service.findById(TEST_ID_ONE))
                .withMessage(ValidationError.CLIENT_NOT_FOUND.getMessage());

        verify(repository).findById(TEST_ID_ONE);
    }
}
