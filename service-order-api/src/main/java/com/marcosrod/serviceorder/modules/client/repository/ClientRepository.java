package com.marcosrod.serviceorder.modules.client.repository;

import com.marcosrod.serviceorder.modules.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ClientRepository extends JpaRepository<Client, Long>, QuerydslPredicateExecutor<Client> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
