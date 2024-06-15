package com.marcosrod.authentication.modules.service;

import com.marcosrod.authentication.config.security.dto.AuthRequest;
import com.marcosrod.authentication.config.security.service.JwtService;
import com.marcosrod.authentication.config.security.service.UserDetailsJpaService;
import com.marcosrod.authentication.modules.common.exception.ValidationException;
import com.marcosrod.authentication.modules.dto.UserRequest;
import com.marcosrod.authentication.modules.dto.UserResponse;
import com.marcosrod.authentication.modules.model.User;
import com.marcosrod.authentication.modules.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserDetailsJpaService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserResponse save(UserRequest request) {
        validateDuplicatedEmail(request.email());

        var savedUser = repository.save(User.of(request, encoder.encode(request.password())));

        return UserResponse.of(savedUser);
    }

    public String login(AuthRequest request) {
        var authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.email());
        } else {
            throw new UsernameNotFoundException("Invalid email or password.");
        }
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
