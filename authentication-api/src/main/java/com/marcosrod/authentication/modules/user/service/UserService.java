package com.marcosrod.authentication.modules.user.service;

import com.marcosrod.authentication.modules.user.dto.UserRequest;
import com.marcosrod.authentication.modules.user.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse save(UserRequest request);
    boolean findUsersById(List<Long> userIds);
}