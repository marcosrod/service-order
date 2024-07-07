package com.marcosrod.authentication.modules.user.service.impl;

import com.marcosrod.authentication.modules.common.enums.ValidationError;
import com.marcosrod.authentication.modules.common.exception.ValidationException;
import com.marcosrod.authentication.modules.user.dto.UserRequest;
import com.marcosrod.authentication.modules.user.dto.UserResponse;
import com.marcosrod.authentication.modules.user.filter.UserFilter;
import com.marcosrod.authentication.modules.user.model.User;
import com.marcosrod.authentication.modules.user.repository.UserRepository;
import com.marcosrod.authentication.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public Page<UserResponse> getAll(Pageable pageable, UserFilter filter) {
        return repository.findAll(filter.toPredicate().build(), pageable)
                .map(UserResponse::of);
    }

    @Override
    public UserResponse save(UserRequest request) {
        validateDuplicatedEmail(request.email());
        var userToSave = new User(request.name(), request.email(), encoder.encode(request.password()),
                request.role());
        var savedUser = repository.save(userToSave);

        return new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(),
                savedUser.getRole().getDescription());
    }

    @Override
    public boolean findUsersById(List<Long> userIds) {
        return repository.findAllById(userIds).size() == userIds.size();
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ValidationException(ValidationError.USER_EMAIL_NOT_FOUND.getMessage()));
    }

    private void validateDuplicatedEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new ValidationException(ValidationError.USER_EMAIL_ALREADY_EXISTS.getMessage());
        }
    }

}
