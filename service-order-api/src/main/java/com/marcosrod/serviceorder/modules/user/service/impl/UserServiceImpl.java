package com.marcosrod.serviceorder.modules.user.service.impl;

import com.marcosrod.serviceorder.modules.common.exception.ValidationException;
import com.marcosrod.serviceorder.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.marcosrod.serviceorder.modules.common.enums.ValidationError.ERROR_SEARCHING_FOR_USER_IDS;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Value("${app.config.services.user-api}")
    private String USER_API_URI;

    private final WebClient webClient;

    public Boolean existsByIds(List<Long> userIds) {
        return webClient
                .get()
                .uri(UriComponentsBuilder
                        .fromHttpUrl(USER_API_URI)
                        .queryParam("userIds", userIds.toArray())
                        .toUriString())
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(error -> {
                    throw new ValidationException(ERROR_SEARCHING_FOR_USER_IDS.getMessage()
                            .concat(error.getMessage()));
                })
                .block();
    }
}
