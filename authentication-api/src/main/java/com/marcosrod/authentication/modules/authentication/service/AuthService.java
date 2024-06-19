package com.marcosrod.authentication.modules.authentication.service;

import com.marcosrod.authentication.modules.authentication.dto.AuthRequest;

public interface AuthService {
    String login(AuthRequest request);
}
