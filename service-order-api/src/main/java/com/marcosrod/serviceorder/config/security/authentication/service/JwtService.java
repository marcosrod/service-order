package com.marcosrod.serviceorder.config.security.authentication.service;

import org.springframework.stereotype.Service;

@Service
public interface JwtService {
    Long getAuthenticatedUserId();
}
