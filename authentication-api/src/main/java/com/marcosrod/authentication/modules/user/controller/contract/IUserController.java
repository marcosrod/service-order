package com.marcosrod.authentication.modules.user.controller.contract;

import com.marcosrod.authentication.modules.user.dto.UserRequest;
import com.marcosrod.authentication.modules.user.dto.UserResponse;
import com.marcosrod.authentication.modules.user.filter.UserFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/users")
public interface IUserController {

    @Operation(summary = "Save new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New User saved."),
            @ApiResponse(responseCode = "400", description = "Invalid request.", content = @Content),
            @ApiResponse(responseCode = "401", description = "User not logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "User hasn't the required permission.", content = @Content)
    })
    @PostMapping
    UserResponse save(@RequestBody @Valid UserRequest request);

    @Operation(summary = "Find users by ids", description = "Check if users exists by ids")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns true or false."),
            @ApiResponse(responseCode = "401", description = "User not logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "User hasn't the required permission.",
                    content = @Content)
    })
    @GetMapping("exists")
    boolean findUsersById(@RequestParam List<Long> userIds);

    @Operation(summary = "Find All Users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns User Page"),
            @ApiResponse(responseCode = "200", description = "Returns Empty Page"),
            @ApiResponse(responseCode = "401", description = "User not logged in.", content = @Content),
            @ApiResponse(responseCode = "403", description = "User hasn't the required permission.", content = @Content)
    })
    @GetMapping
    Page<UserResponse> getAll(Pageable pageable, UserFilter filter);
}
