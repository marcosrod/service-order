package com.marcosrod.serviceorder.config.security.authentication.service.impl;

import com.marcosrod.serviceorder.config.security.authentication.service.JwtService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    public Long getAuthenticatedUserId() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getCredentials();

        return Long.valueOf(jwt.getClaimAsString("userId"));
    }
}
