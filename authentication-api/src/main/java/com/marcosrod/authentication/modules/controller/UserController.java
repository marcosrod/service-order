package com.marcosrod.authentication.modules.controller;

import com.marcosrod.authentication.modules.dto.UserRequest;
import com.marcosrod.authentication.modules.dto.UserResponse;
import com.marcosrod.authentication.modules.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    @PostMapping
    public UserResponse save(UserRequest request) {
        return service.save(request);
    }

    @GetMapping
    public boolean existsByIds(@RequestParam List<Long> userIds) {
        return service.existsByIds(userIds);
    }
}
