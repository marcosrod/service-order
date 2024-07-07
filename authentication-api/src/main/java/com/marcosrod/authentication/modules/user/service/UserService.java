package com.marcosrod.authentication.modules.user.service;

import com.marcosrod.authentication.modules.user.dto.UserRequest;
import com.marcosrod.authentication.modules.user.dto.UserResponse;
import com.marcosrod.authentication.modules.user.filter.UserFilter;
import com.marcosrod.authentication.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponse save(UserRequest request);
    boolean findUsersById(List<Long> userIds);
    User findByEmail(String email);
    Page<UserResponse> getAll(Pageable pageable, UserFilter filter);
}
