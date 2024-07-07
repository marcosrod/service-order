package com.marcosrod.serviceorder.modules.equipment.controller;

import com.marcosrod.serviceorder.modules.equipment.dto.EquipmentRequest;
import com.marcosrod.serviceorder.modules.equipment.service.impl.EquipmentServiceImpl;
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

import static com.marcosrod.serviceorder.modules.common.helper.ConstantUtil.*;
import static com.marcosrod.serviceorder.modules.common.helper.JsonHelper.asJsonString;
import static com.marcosrod.serviceorder.modules.common.helper.JwtHelper.TEST_USER_EMAIL;
import static com.marcosrod.serviceorder.modules.common.helper.JwtHelper.getJwtToken;
import static com.marcosrod.serviceorder.modules.equipment.helper.EquipmentHelper.*;
import static com.marcosrod.serviceorder.modules.order.helper.OrderHelper.getPageable;
import static java.lang.String.valueOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EquipmentControllerTest {

    private static final String API_URI = "/api/equipments";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private EquipmentServiceImpl service;

    @SneakyThrows
    @Test
    void getAll_shouldReturnOkAndPageEquipmentResponse_whenRequested() {
        var equipmentResponse = getEquipmentResponse();
        var pageable = getPageable();
        var equipmentResponsePage = getEquipmentResponsePage();

        doReturn(equipmentResponsePage).when(service).getAll(eq(pageable), any());

        mvc.perform(MockMvcRequestBuilders.get(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.R.getAuthority()))
                        .param(PAGEABLE_PAGE_NUMBER, valueOf(pageable.getPageNumber()))
                        .param(PAGEABLE_PAGE_SIZE, valueOf(pageable.getPageSize()))
                        .param(PAGEABLE_SORT, ID_ATTRIBUTE)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(equipmentResponse.id()))
                .andExpect(jsonPath("$.content[0].type").value(equipmentResponse.type()))
                .andExpect(jsonPath("$.content[0].model").value(equipmentResponse.model()));

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
    void save_shouldReturnOkAndEquipmentResponse_whenRequested() {
        var equipmentRequest = getEquipmentRequest();
        var equipmentResponse = getEquipmentResponse();

        doReturn(equipmentResponse).when(service).save(equipmentRequest);

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.R.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(equipmentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(equipmentResponse.id()))
                .andExpect(jsonPath("$.type").value(equipmentResponse.type()))
                .andExpect(jsonPath("$.model").value(equipmentResponse.model()));

        verify(service).save(equipmentRequest);
    }

    @SneakyThrows
    @Test
    void save_shouldReturnBadRequestAndValidationError_whenInvalidRequest() {
        var invalidEquipmentRequest = new EquipmentRequest(getEquipmentRequest().type(), EMPTY_STRING);

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.R.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidEquipmentRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$")
                        .value("Error parsing the field: model -> must not be empty"));

        verify(service, never()).save(invalidEquipmentRequest);
    }

    @SneakyThrows
    @Test
    void save_shouldReturnForbidden_whenUserHasNoPermission() {
        var equipmentRequest = getEquipmentRequest();

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, Role.T.getAuthority()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(equipmentRequest)))
                .andExpect(status().isForbidden());

        verify(service, never()).save(equipmentRequest);
    }

    @SneakyThrows
    @Test
    void save_shouldReturnUnauthorized_whenUserNotAuthenticated() {
        var equipmentRequest = getEquipmentRequest();

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(equipmentRequest)))
                .andExpect(status().isUnauthorized());

        verify(service, never()).save(equipmentRequest);
    }
}
