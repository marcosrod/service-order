package com.marcosrod.authentication.modules.authentication.controller;

import com.marcosrod.authentication.modules.authentication.dto.AuthRequest;
import com.marcosrod.authentication.modules.authentication.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping
    public String login(@RequestBody @Valid AuthRequest authRequest) {
        return service.login(authRequest);
    }
}
