package com.marcosrod.authentication.modules.user.service.impl;

import com.marcosrod.authentication.modules.common.enums.ValidationError;
import com.marcosrod.authentication.modules.common.exception.ValidationException;
import com.marcosrod.authentication.modules.user.dto.UserRequest;
import com.marcosrod.authentication.modules.user.dto.UserResponse;
import com.marcosrod.authentication.modules.user.model.User;
import com.marcosrod.authentication.modules.user.repository.UserRepository;
import com.marcosrod.authentication.modules.user.service.UserService;
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
            throw new ValidationException(ValidationError.USER_EMAIL_ALREADY_EXISTS.getMessage());
        }
    }

}
