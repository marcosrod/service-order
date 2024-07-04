package com.marcosrod.authentication.modules.authentication.controller.contract;

import com.marcosrod.authentication.modules.authentication.dto.AuthRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
public interface IAuthController {

    @Operation(summary = "Login with email and password", description = "Login and get a new Bearer Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in."),
            @ApiResponse(responseCode = "401", description = "Invalid user email or password.",
            content = @Content)
    })
    @PostMapping
    String login(@RequestBody @Valid AuthRequest authRequest);
}
