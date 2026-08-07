package com.careercopilot.auth.service;

import com.careercopilot.auth.dto.request.LoginRequest;
import com.careercopilot.auth.dto.request.RefreshTokenRequest;
import com.careercopilot.auth.dto.request.RegisterRequest;
import com.careercopilot.auth.dto.response.AuthResponse;
import com.careercopilot.auth.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(RefreshTokenRequest request);

    void logout(String refreshToken);
}