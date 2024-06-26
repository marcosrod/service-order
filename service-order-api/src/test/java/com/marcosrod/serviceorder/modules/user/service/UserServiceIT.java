package com.marcosrod.serviceorder.modules.user.service;

import com.marcosrod.serviceorder.modules.common.enums.ValidationError;
import com.marcosrod.serviceorder.modules.common.exception.ValidationException;
import com.marcosrod.serviceorder.modules.user.service.impl.UserServiceImpl;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import static com.marcosrod.serviceorder.modules.user.helper.WebClientHelper.getMockResponse;
import static com.marcosrod.serviceorder.modules.user.helper.WebClientHelper.getUserIds;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserServiceIT {

    public static MockWebServer mockTargetApi;
    @InjectMocks
    private UserServiceImpl service;

    @SneakyThrows
    @BeforeAll
    static void setUp() {
        mockTargetApi = new MockWebServer();
        mockTargetApi.start();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = mockTargetApi.url("/api/users/exists").toString();
        service = new UserServiceImpl(WebClient.create(baseUrl));
        ReflectionTestUtils.setField(service, "USER_API_URI", baseUrl);
    }

    @SneakyThrows
    @AfterAll
    static void tearDown() {
        mockTargetApi.shutdown();
    }

    @SneakyThrows
    @Test
    void existsByIds_shouldReturnTrue_whenUserIdsExists() {
        mockTargetApi.enqueue(getMockResponse("true"));

        var result = service.existsByIds(getUserIds());

        assertTrue(result);
    }

    @SneakyThrows
    @Test
    void existsByIds_shouldReturnFalse_whenUserIdsDoesNotExists() {
        mockTargetApi.enqueue(getMockResponse("false"));

        var result = service.existsByIds(getUserIds());

        assertFalse(result);
    }

    @SneakyThrows
    @Test
    void existsByIds_shouldThrowValidationException_whenServerErrorOccurs() {
        mockTargetApi.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error"));

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> service.existsByIds(getUserIds()))
                .withMessage(ValidationError.ERROR_SEARCHING_FOR_USER_IDS.getMessage()
                        .concat("500 Internal Server Error from GET ")
                        .concat(String.format("http://localhost:%s/api/users/exists", mockTargetApi.getPort())));
    }
}