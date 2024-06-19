package com.marcosrod.authentication.modules.user.controller;

import com.marcosrod.authentication.modules.user.dto.UserRequest;
import com.marcosrod.authentication.modules.user.dto.UserResponse;
import com.marcosrod.authentication.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    @PostMapping
    public UserResponse save(@RequestBody UserRequest request) {
        return service.save(request);
    }

    @GetMapping("exists")
    public boolean findUsersById(@RequestParam List<Long> userIds) {
        return service.findUsersById(userIds);
    }
}
