package com.marcosrod.serviceorder.modules.client.controller.contract;

import com.marcosrod.serviceorder.modules.client.dto.ClientRequest;
import com.marcosrod.serviceorder.modules.client.dto.ClientResponse;
import com.marcosrod.serviceorder.modules.client.filter.ClientFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/clients")
public interface IClientController {

    @Operation(summary = "Save new Client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New Client saved."),
            @ApiResponse(responseCode = "400", description = "Invalid request.", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "User hasn't the required permission.", content = @Content)
    })
    @PostMapping
    ClientResponse save(@RequestBody @Valid ClientRequest request);

    @Operation(summary = "Find All Clients.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns Client Page"),
            @ApiResponse(responseCode = "200", description = "Returns Empty Page"),
            @ApiResponse(responseCode = "401", description = "User not logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "User hasn't the required permission.", content = @Content)
    })
    @GetMapping
    Page<ClientResponse> getAll(Pageable pageable, ClientFilter filter);
}
