package com.marcosrod.authentication.modules.user.controller;

import com.marcosrod.authentication.modules.user.controller.contract.IUserController;
import com.marcosrod.authentication.modules.user.dto.UserRequest;
import com.marcosrod.authentication.modules.user.dto.UserResponse;
import com.marcosrod.authentication.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController implements IUserController {

    private final UserService service;

    @Override
    public UserResponse save(UserRequest request) {
        return service.save(request);
    }

    @Override
    public boolean findUsersById(List<Long> userIds) {
        return service.findUsersById(userIds);
    }
}
