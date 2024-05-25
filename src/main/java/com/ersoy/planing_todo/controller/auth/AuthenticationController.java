package com.ersoy.planing_todo.controller.auth;

import com.ersoy.planing_todo.domain.dto.request.AuthenticationRequestDTO;
import com.ersoy.planing_todo.domain.dto.request.RegisterRequestDTO;
import com.ersoy.planing_todo.domain.dto.response.AuthenticationResponseDTO;
import com.ersoy.planing_todo.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "User Registration",
            description = "Registers a new user with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registration successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request details")
    })
    public ResponseEntity<AuthenticationResponseDTO>
    register(@RequestBody RegisterRequestDTO request) {
        log.info("Request to register process started with: {}", request);
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authentication")
    @Operation(summary = "User Authentication",
            description = "Authenticates a user with the provided credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authentication successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    public ResponseEntity<AuthenticationResponseDTO>
    register(@RequestBody AuthenticationRequestDTO request) {
        log.info("Request to authentication process started with: {}", request);
        return ResponseEntity.ok(authenticationService.authenticate(request));

    }
}
