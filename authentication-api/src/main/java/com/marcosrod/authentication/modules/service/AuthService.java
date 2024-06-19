package com.marcosrod.authentication.modules.service;

import com.marcosrod.authentication.config.security.dto.AuthRequest;

public interface AuthService {
    String login(AuthRequest request);
}
