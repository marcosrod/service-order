package com.marcosrod.authentication.modules.service.impl;

import com.marcosrod.authentication.config.security.dto.AuthRequest;
import com.marcosrod.authentication.config.security.service.JwtService;
import com.marcosrod.authentication.config.security.service.UserDetailsJpaService;
import com.marcosrod.authentication.modules.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsJpaService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String login(AuthRequest request) {
        var authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.email(), authentication.getAuthorities());
        } else {
            throw new UsernameNotFoundException("Invalid email or password.");
        }
    }
}
