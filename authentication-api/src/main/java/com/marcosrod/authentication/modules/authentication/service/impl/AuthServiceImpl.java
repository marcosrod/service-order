package com.marcosrod.authentication.modules.authentication.service.impl;

import com.marcosrod.authentication.modules.authentication.dto.AuthRequest;
import com.marcosrod.authentication.config.security.service.JwtService;
import com.marcosrod.authentication.modules.authentication.service.AuthService;
import com.marcosrod.authentication.modules.common.enums.ValidationError;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String login(AuthRequest request) {
        var authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.email(), authentication.getAuthorities());
        } else {
            throw new UsernameNotFoundException(ValidationError.USER_EMAIL_PASSWORD_NOT_FOUND.getMessage());
        }
    }
}
