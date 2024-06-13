package com.marcosrod.serviceorder.modules.client.repository;

import com.marcosrod.serviceorder.modules.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
