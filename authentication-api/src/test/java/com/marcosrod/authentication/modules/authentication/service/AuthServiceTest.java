package com.marcosrod.authentication.modules.authentication.service;

import com.marcosrod.authentication.config.security.service.impl.JwtServiceImpl;
import com.marcosrod.authentication.modules.authentication.service.impl.AuthServiceImpl;
import com.marcosrod.authentication.modules.common.enums.ValidationError;
import com.marcosrod.authentication.modules.user.enums.Role;
import com.marcosrod.authentication.modules.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static com.marcosrod.authentication.modules.authentication.helper.AuthHelper.*;
import static com.marcosrod.authentication.modules.common.helper.ConstantUtil.TEST_ID_ONE;
import static com.marcosrod.authentication.modules.user.helper.UserHelper.getSavedUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl service;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private JwtServiceImpl jwtService;
    @Mock
    private Authentication authentication;

    @Test
    void login_shouldReturnUserToken_whenValidEmailAndPassword() {
        var userId = 1L;
        var authRequest = getAuthRequest();
        var userEmail = authRequest.email();
        var authorities = getUserAuthorities();
        var usernamePasswordAuthToken = getUserPasswordAuthToken();
        var finalToken = getToken();

        doReturn(true).when(authentication).isAuthenticated();
        doReturn(getUserAuthorities()).when(authentication).getAuthorities();
        doReturn(authentication).when(authenticationManager).authenticate(usernamePasswordAuthToken);
        doReturn(getSavedUser(Role.R)).when(userService).findByEmail(userEmail);
        doReturn(finalToken).when(jwtService).generateToken(userId, userEmail, authorities);

        assertThat(service.login(authRequest))
                .isEqualTo(finalToken);

        verify(authenticationManager).authenticate(usernamePasswordAuthToken);
        verify(userService).findByEmail(userEmail);
        verify(jwtService).generateToken(userId, userEmail, authorities);
    }

    @Test
    void login_shouldThrowException_whenInvalidEmailAndPassword() {
        var authRequest = getAuthRequest();
        var userEmail = authRequest.email();
        var usernamePasswordAuthToken = getUserPasswordAuthToken();

        doReturn(false).when(authentication).isAuthenticated();
        doReturn(authentication).when(authenticationManager).authenticate(usernamePasswordAuthToken);

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> service.login(authRequest))
                .withMessage(ValidationError.USER_EMAIL_PASSWORD_NOT_FOUND.getMessage());

        verify(authenticationManager).authenticate(usernamePasswordAuthToken);
        verify(userService, never()).findByEmail(userEmail);
        verify(jwtService, never()).generateToken(TEST_ID_ONE, userEmail, List.of());
    }
}
