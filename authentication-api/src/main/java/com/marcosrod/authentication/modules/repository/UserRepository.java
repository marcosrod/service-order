package com.marcosrod.authentication.modules.repository;

import com.marcosrod.authentication.modules.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
