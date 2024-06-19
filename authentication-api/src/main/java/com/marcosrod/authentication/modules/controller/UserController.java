package com.marcosrod.authentication.modules.controller;

import com.marcosrod.authentication.config.security.dto.AuthRequest;
import com.marcosrod.authentication.modules.dto.UserRequest;
import com.marcosrod.authentication.modules.dto.UserResponse;
import com.marcosrod.authentication.modules.service.AuthService;
import com.marcosrod.authentication.modules.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    private final AuthService authService;

    @PostMapping
    public UserResponse save(@RequestBody UserRequest request) {
        return service.save(request);
    }

    @GetMapping("exists")
    public boolean findUsersById(@RequestParam List<Long> userIds) {
        return service.findUsersById(userIds);
    }

    @PostMapping("login")
    public String login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }
}
