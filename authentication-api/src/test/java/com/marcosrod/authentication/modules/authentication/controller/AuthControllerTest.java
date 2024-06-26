package com.marcosrod.authentication.modules.authentication.controller;

import com.marcosrod.authentication.modules.authentication.service.AuthService;
import com.marcosrod.authentication.modules.common.exception.ValidationException;
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

import static com.marcosrod.authentication.modules.authentication.helper.AuthHelper.*;
import static com.marcosrod.authentication.modules.authentication.helper.AuthHelper.getToken;
import static com.marcosrod.authentication.modules.common.enums.ValidationError.USER_EMAIL_PASSWORD_NOT_FOUND;
import static com.marcosrod.authentication.modules.common.helper.JsonHelper.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    private static final String API_URI = "/api/auth";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthService service;

    @SneakyThrows
    @Test
    void login_shouldReturnToken_whenValidAuthRequest() {
        var authRequest = getAuthRequest();
        var token = getToken();

        doReturn(token).when(service).login(authRequest);

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(token));

        verify(service).login(authRequest);
    }

    @SneakyThrows
    @Test
    void login_shouldReturnInternalServerError_whenInvalidAuthRequest() {
        var authRequest = getAuthRequest();

        doThrow(new ValidationException(USER_EMAIL_PASSWORD_NOT_FOUND.getMessage()))
                .when(service).login(authRequest);

        mvc.perform(MockMvcRequestBuilders.post(API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(authRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$").value(USER_EMAIL_PASSWORD_NOT_FOUND.getMessage()));

        verify(service).login(authRequest);
    }
}
