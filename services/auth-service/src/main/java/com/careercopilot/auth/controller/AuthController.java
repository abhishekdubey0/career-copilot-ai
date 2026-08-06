package com.careercopilot.auth.controller;

import com.careercopilot.auth.common.ApiResponse;
import com.careercopilot.auth.common.ResponseBuilder;
import com.careercopilot.auth.dto.request.RegisterRequest;
import com.careercopilot.auth.dto.response.AuthResponse;
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
}
