package com.marcosrod.authentication.modules.user.controller;

import com.marcosrod.authentication.config.security.service.UserDetailsJpaService;
import com.marcosrod.authentication.modules.user.service.impl.UserServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.marcosrod.authentication.modules.authentication.helper.AuthHelper.*;
import static com.marcosrod.authentication.modules.common.helper.ConstantUtil.*;
import static com.marcosrod.authentication.modules.common.helper.JsonHelper.asJsonString;
import static com.marcosrod.authentication.modules.common.helper.TestHelper.getPageable;
import static com.marcosrod.authentication.modules.user.enums.Role.R;
import static com.marcosrod.authentication.modules.user.enums.Role.T;
import static com.marcosrod.authentication.modules.user.helper.UserHelper.*;
import static java.lang.String.valueOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final String API_URI = "/api/users";
    private static final String EXISTS_ENDPOINT = "/exists";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserServiceImpl service;
    @MockBean
    private UserDetailsJpaService userDetailsJpaService;

    @SneakyThrows
    @Test
    void getAll_shouldReturnOkAndPageUserResponse_whenRequested() {
        var userResponse = getUserResponse(R);
        var pageable = getPageable();
        var userResponsePage = getUserResponsePage(R);

        doReturn(getUserDetails(R)).when(userDetailsJpaService).loadUserByUsername(TEST_USER_EMAIL);
        doReturn(userResponsePage).when(service).getAll(eq(pageable), any());

        mvc.perform(MockMvcRequestBuilders.get(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, R.getAuthority()))
                        .param(PAGEABLE_PAGE_NUMBER, valueOf(pageable.getPageNumber()))
                        .param(PAGEABLE_PAGE_SIZE, valueOf(pageable.getPageSize()))
                        .param(PAGEABLE_SORT, ID_ATTRIBUTE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(userResponse.id()))
                .andExpect(jsonPath("$.content[0].name").value(userResponse.name()))
                .andExpect(jsonPath("$.content[0].email").value(userResponse.email()))
                .andExpect(jsonPath("$.content[0].role").value(userResponse.role()));

        verify(service).getAll(eq(pageable), any());
    }

    @SneakyThrows
    @Test
    void getAll_shouldReturnForbidden_whenUserHasNoPermission() {
        doReturn(getUserDetails(T)).when(userDetailsJpaService).loadUserByUsername(TEST_USER_EMAIL);

        mvc.perform(MockMvcRequestBuilders.get(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, T.getAuthority())))
                .andExpect(status().isForbidden());

        verify(service, never()).getAll(any(), any());
    }

    @SneakyThrows
    @Test
    void getAll_shouldReturnUnauthorized_whenUserNotAuthenticated() {
        mvc.perform(MockMvcRequestBuilders.get(API_URI))
                .andExpect(status().isUnauthorized());

        verify(service, never()).getAll(any(), any());
    }

    @SneakyThrows
    @Test
    void save_shouldReturnOkAndUserResponse_whenRequested() {
        var userRequest = getUserRequest(R);
        var userResponse = getUserResponse(R);

        doReturn(getUserResponse(R)).when(service).save(userRequest);
        doReturn(getUserDetails(R)).when(userDetailsJpaService).loadUserByUsername(TEST_USER_EMAIL);

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, R.getAuthority()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userResponse.id()))
                .andExpect(jsonPath("$.name").value(userResponse.name()))
                .andExpect(jsonPath("$.email").value(userResponse.email()))
                .andExpect(jsonPath("$.role").value(userResponse.role()));

        verify(service).save(userRequest);
        verify(userDetailsJpaService).loadUserByUsername(TEST_USER_EMAIL);
    }

    @SneakyThrows
    @Test
    void save_shouldReturnForbidden_whenUserHasNoPermission() {
        var userRequest = getUserRequest(R);

        doReturn(getUserDetails(T)).when(userDetailsJpaService).loadUserByUsername(TEST_USER_EMAIL);

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, T.getAuthority()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequest)))
                .andExpect(status().isForbidden());

        verify(service, never()).save(userRequest);
        verify(userDetailsJpaService).loadUserByUsername(TEST_USER_EMAIL);
    }

    @SneakyThrows
    @Test
    void save_shouldReturnUnauthorized_whenUserNotAuthenticated() {
        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(getUserRequest(R))))
                .andExpect(status().isUnauthorized());
    }

    @SneakyThrows
    @Test
    void findUsersById_shouldReturnOkAndTrue_whenUsersExists() {
        var userIds = List.of(TEST_ID_ONE, TEST_ID_TWO);

        doReturn(true).when(service).findUsersById(userIds);
        doReturn(getUserDetails(R)).when(userDetailsJpaService).loadUserByUsername(TEST_USER_EMAIL);

        mvc.perform(MockMvcRequestBuilders.get(API_URI + EXISTS_ENDPOINT)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, R.getAuthority()))
                        .param("userIds", "1, 2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("true"));

        verify(service).findUsersById(userIds);
        verify(userDetailsJpaService).loadUserByUsername(TEST_USER_EMAIL);
    }

    @SneakyThrows
    @Test
    void findUsersById_shouldReturnOkAndFalse_whenUsersDoesNotExists() {
        var userIds = List.of(TEST_ID_ONE, TEST_ID_TWO);

        doReturn(false).when(service).findUsersById(userIds);
        doReturn(getUserDetails(R)).when(userDetailsJpaService).loadUserByUsername(TEST_USER_EMAIL);

        mvc.perform(MockMvcRequestBuilders.get(API_URI + EXISTS_ENDPOINT)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, R.getAuthority()))
                        .param("userIds", "1, 2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("false"));

        verify(service).findUsersById(userIds);
        verify(userDetailsJpaService).loadUserByUsername(TEST_USER_EMAIL);
    }

    @SneakyThrows
    @Test
    void findUsersById_shouldReturnForbidden_whenUserHasNoPermission() {
        doReturn(getUserDetails(T)).when(userDetailsJpaService).loadUserByUsername(TEST_USER_EMAIL);

        mvc.perform(MockMvcRequestBuilders.get(API_URI + EXISTS_ENDPOINT)
                        .header(AUTHORIZATION_HEADER,
                                BEARER_TOKEN_PREFIX + getJwtToken(TEST_USER_EMAIL, T.getAuthority())))
                .andExpect(status().isForbidden());

        verify(service, never()).findUsersById(any());
        verify(userDetailsJpaService).loadUserByUsername(TEST_USER_EMAIL);
    }

    @SneakyThrows
    @Test
    void findUsersById_shouldReturnUnauthorized_whenUserNotAuthenticated() {
        mvc.perform(MockMvcRequestBuilders.get(API_URI + EXISTS_ENDPOINT))
                .andExpect(status().isUnauthorized());

        verify(service, never()).findUsersById(any());
        verify(userDetailsJpaService, never()).loadUserByUsername(any());
    }
}
