package com.marcosrod.serviceorder.modules.user.service.impl;

import com.marcosrod.serviceorder.common.exception.ValidationException;
import com.marcosrod.serviceorder.config.WebClientConfig;
import com.marcosrod.serviceorder.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Value("${app.config.services.user-api}")
    private String USER_API_URI;
    private final WebClientConfig webClient;

    public Boolean existsByIds(List<Long> userIds) {
        return webClient.rest()
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
