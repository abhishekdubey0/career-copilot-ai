package com.careercopilot.auth.controller;

import com.careercopilot.auth.common.ApiResponse;
import com.careercopilot.auth.common.ResponseBuilder;
import com.careercopilot.auth.dto.request.LoginRequest;
import com.careercopilot.auth.dto.request.RefreshTokenRequest;
import com.careercopilot.auth.dto.request.RegisterRequest;
import com.careercopilot.auth.dto.response.AuthResponse;
import com.careercopilot.auth.dto.response.LoginResponse;
import com.careercopilot.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        AuthResponse response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseBuilder.success(
                                HttpStatus.CREATED,
                                "User registered successfully",
                                response
                        )
                );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK,
                        "Login successful",
                        response
                )
        );
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {

        LoginResponse response = authService.refreshToken(request);

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK,
                        "Access token refreshed successfully",
                        response
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @RequestBody RefreshTokenRequest request) {

        authService.logout(request.getRefreshToken());

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK,
                        "Logged out successfully",
                        null
                )
        );
    }
}
