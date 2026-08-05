package com.careercopilot.auth.service;

import com.careercopilot.auth.dto.request.RegisterRequest;
import com.careercopilot.auth.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);
}