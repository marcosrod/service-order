package com.marcosrod.authentication.modules.service;

import com.marcosrod.authentication.modules.common.exception.ValidationException;
import com.marcosrod.authentication.modules.dto.UserRequest;
import com.marcosrod.authentication.modules.dto.UserResponse;
import com.marcosrod.authentication.modules.model.User;
import com.marcosrod.authentication.modules.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    public UserResponse save(UserRequest request) {
        validateDuplicatedEmail(request.email());

        var savedUser = repository.save(User.of(request));

        return UserResponse.of(savedUser);
    }

    public boolean existsByIds(List<Long> userIds) {
        return repository.findAllById(userIds).size() == userIds.size();
    }

    private void validateDuplicatedEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new ValidationException("This email is already registered.");
        }
    }

}
