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

import static com.marcosrod.serviceorder.modules.client.helper.ClientHelper.getClientRequest;
import static com.marcosrod.serviceorder.modules.client.helper.ClientHelper.getClientResponse;
import static com.marcosrod.serviceorder.modules.common.helper.ConstantUtil.*;
import static com.marcosrod.serviceorder.modules.common.helper.JsonHelper.asJsonString;
import static com.marcosrod.serviceorder.modules.common.helper.JwtHelper.*;
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
