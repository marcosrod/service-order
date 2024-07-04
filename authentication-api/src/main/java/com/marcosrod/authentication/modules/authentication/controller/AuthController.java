package com.marcosrod.authentication.modules.authentication.controller;

import com.marcosrod.authentication.modules.authentication.controller.contract.IAuthController;
import com.marcosrod.authentication.modules.authentication.dto.AuthRequest;
import com.marcosrod.authentication.modules.authentication.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController implements IAuthController {

    private final AuthService service;

    @Override
    public String login(AuthRequest authRequest) {
        return service.login(authRequest);
    }
}
