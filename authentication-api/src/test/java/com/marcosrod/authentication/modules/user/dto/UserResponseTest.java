package com.marcosrod.authentication.modules.user.dto;

import org.junit.jupiter.api.Test;

import static com.marcosrod.authentication.modules.user.enums.Role.R;
import static com.marcosrod.authentication.modules.user.enums.Role.T;
import static com.marcosrod.authentication.modules.user.helper.UserHelper.getSavedUser;
import static com.marcosrod.authentication.modules.user.helper.UserHelper.getUserResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserResponseTest {

    @Test
    void of_shouldReturnUserResponse_whenReceptionistUserRequested() {
        var expectedResponse = getUserResponse(R);

        assertEquals(expectedResponse, UserResponse.of(getSavedUser(R)));
    }

    @Test
    void of_shouldReturnUserResponse_whenTechnicianUserRequested() {
        var expectedResponse = getUserResponse(T);

        assertEquals(expectedResponse, UserResponse.of(getSavedUser(T)));
    }
}
