package com.marcosrod.authentication.config.security.service;

import com.marcosrod.authentication.config.security.dto.UserAuthDetails;
import com.marcosrod.authentication.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsJpaService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = repository.findByEmail(email);

        return user.map(UserAuthDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid user email."));
    }
}
