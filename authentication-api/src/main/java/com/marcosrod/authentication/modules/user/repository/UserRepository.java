package com.marcosrod.authentication.modules.user.repository;

import com.marcosrod.authentication.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
