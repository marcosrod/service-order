package com.marcosrod.authentication.modules.service;

import com.marcosrod.authentication.modules.dto.UserRequest;
import com.marcosrod.authentication.modules.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse save(UserRequest request);
    boolean findUsersById(List<Long> userIds);
}
