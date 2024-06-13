package com.marcosrod.serviceorder.modules.user.service;

import com.marcosrod.serviceorder.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class UserService {

    @Value("${app.config.services.user-api}")
    private String USER_API_URI;

    public Boolean existsByIds(List<Long> userIds) {
        return WebClient.create()
                .get()
                .uri(UriComponentsBuilder
                        .fromHttpUrl(USER_API_URI)
                        .queryParam("userIds", userIds.toArray())
                        .toUriString())
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(error -> {
                    throw new ValidationException("Error searching for user ids: ".concat(error.getMessage()));
                })
                .block();
    }
}
