package com.marcosrod.authentication.modules.service.impl;

import com.marcosrod.authentication.modules.common.exception.ValidationException;
import com.marcosrod.authentication.modules.dto.UserRequest;
import com.marcosrod.authentication.modules.dto.UserResponse;
import com.marcosrod.authentication.modules.model.User;
import com.marcosrod.authentication.modules.repository.UserRepository;
import com.marcosrod.authentication.modules.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserResponse save(UserRequest request) {
        validateDuplicatedEmail(request.email());

        var savedUser = repository.save(User.of(request, encoder.encode(request.password())));

        return UserResponse.of(savedUser);
    }

    public boolean findUsersById(List<Long> userIds) {
        return repository.findAllById(userIds).size() == userIds.size();
    }

    private void validateDuplicatedEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new ValidationException("This email is already registered.");
        }
    }

}
