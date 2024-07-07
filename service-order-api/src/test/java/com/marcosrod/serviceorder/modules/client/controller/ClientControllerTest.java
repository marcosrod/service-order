package com.marcosrod.serviceorder.modules.client.controller;

import com.marcosrod.serviceorder.modules.client.dto.ClientRequest;
import com.marcosrod.serviceorder.modules.client.service.ClientServiceImpl;
import com.marcosrod.serviceorder.modules.user.enums.Role;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.marcosrod.serviceorder.modules.client.helper.ClientHelper.*;
import static com.marcosrod.serviceorder.modules.common.helper.ConstantUtil.*;
import static com.marcosrod.serviceorder.modules.common.helper.JsonHelper.asJsonString;
import static com.marcosrod.serviceorder.modules.common.helper.JwtHelper.*;
import static com.marcosrod.serviceorder.modules.order.helper.OrderHelper.getPageable;
import static java.lang.String.valueOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    private static final String API_URI = "/api/clients";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ClientServiceImpl service;

    @SneakyThrows
    @Test
    void getAll_shouldReturnOkAndPageClientResponse_whenRequested() {
        var clientResponse = getClientResponse();
        var pageable = getPageable();
        var clientResponsePage = getClientResponsePage();

        doReturn(clientResponsePage).when(service).getAll(eq(pageable), any());

        mvc.perform(MockMvcRequestBuilders.get(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.R.getAuthority()))
                        .param(PAGEABLE_PAGE_NUMBER, valueOf(pageable.getPageNumber()))
                        .param(PAGEABLE_PAGE_SIZE, valueOf(pageable.getPageSize()))
                        .param(PAGEABLE_SORT, ID_ATTRIBUTE)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(clientResponse.id()))
                .andExpect(jsonPath("$.content[0].name").value(clientResponse.name()))
                .andExpect(jsonPath("$.content[0].address").value(clientResponse.address()))
                .andExpect(jsonPath("$.content[0].phone").value(clientResponse.phone()))
                .andExpect(jsonPath("$.content[0].email").value(clientResponse.email()));

        verify(service).getAll(eq(pageable), any());
    }

    @SneakyThrows
    @Test
    void getAll_shouldReturnForbidden_whenUserHasNoPermission() {
        mvc.perform(MockMvcRequestBuilders.get(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.T.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isForbidden());

        verify(service, never()).getAll(any(), any());
    }

    @SneakyThrows
    @Test
    void getAll_shouldReturnUnauthorized_whenUserNotAuthenticated() {
        mvc.perform(MockMvcRequestBuilders.get(API_URI)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isUnauthorized());

        verify(service, never()).getAll(any(), any());
    }

    @SneakyThrows
    @Test
    void save_shouldReturnOkAndClientResponse_whenRequested() {
        var clientRequest = getClientRequest();
        var clientResponse = getClientResponse();

        doReturn(clientResponse).when(service).save(clientRequest);

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.R.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientResponse.id()))
                .andExpect(jsonPath("$.name").value(clientResponse.name()))
                .andExpect(jsonPath("$.address").value(clientResponse.address()))
                .andExpect(jsonPath("$.phone").value(clientResponse.phone()))
                .andExpect(jsonPath("$.email").value(clientResponse.email()));

        verify(service).save(clientRequest);
    }

    @SneakyThrows
    @Test
    void save_shouldReturnBadRequestAndValidationError_whenInvalidRequest() {
        var clientRequest = getClientRequest();
        var invalidClientRequest = new ClientRequest(clientRequest.name(), EMPTY_STRING,
                clientRequest.phone(), clientRequest.email());

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.R.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidClientRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$")
                        .value("Error parsing the field: address -> must not be empty"));

        verify(service, never()).save(invalidClientRequest);
    }

    @SneakyThrows
    @Test
    void save_shouldReturnForbidden_whenUserHasNoPermission() {
        var clientRequest = getClientRequest();

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.T.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(clientRequest)))
                .andExpect(status().isForbidden());

        verify(service, never()).save(clientRequest);
    }

    @SneakyThrows
    @Test
    void save_shouldReturnUnauthorized_whenUserNotAuthenticated() {
        var clientRequest = getClientRequest();

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(clientRequest)))
                .andExpect(status().isUnauthorized());

        verify(service, never()).save(clientRequest);
    }
}
