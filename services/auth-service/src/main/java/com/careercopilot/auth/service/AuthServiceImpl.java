package com.careercopilot.auth.service;


import com.careercopilot.auth.dto.request.LoginRequest;
import com.careercopilot.auth.dto.request.RefreshTokenRequest;
import com.careercopilot.auth.dto.request.RegisterRequest;
import com.careercopilot.auth.dto.response.AuthResponse;
import com.careercopilot.auth.dto.response.LoginResponse;
import com.careercopilot.auth.entity.RefreshToken;
import com.careercopilot.auth.entity.Role;
import com.careercopilot.auth.entity.User;
import com.careercopilot.auth.entity.UserStatus;
import com.careercopilot.auth.exception.EmailAlreadyExistsException;
import com.careercopilot.auth.exception.ResourceNotFoundException;
import com.careercopilot.auth.repository.RefreshTokenRepository;
import com.careercopilot.auth.repository.RoleRepository;
import com.careercopilot.auth.repository.UserRepository;
import com.careercopilot.auth.security.CustomUserDetails;
import com.careercopilot.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("ROLE_USER not found"));

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .status(UserStatus.ACTIVE)
                .emailVerified(false)
                .roles(Set.of(userRole))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return new AuthResponse(
                user.getId(),
                user.getEmail()
        );
    }

    public LoginResponse login(LoginRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        UserDetails userDetails = new CustomUserDetails(user);

        String accessToken = jwtService.generateToken(userDetails);

        String refreshToken = jwtService.generateRefreshToken(userDetails);

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .expiresAt(jwtService.getRefreshTokenExpiry())
                .revoked(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .build();

        refreshTokenRepository.save(refreshTokenEntity);

        return new LoginResponse(
                accessToken,
                refreshToken
        );
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshTokenEntity = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Refresh token not found"));

        if (refreshTokenEntity.isRevoked()) {
            throw new RuntimeException("Refresh token has been revoked");
        }

        if (refreshTokenEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token has expired");
        }

        User user = refreshTokenEntity.getUser();

        UserDetails userDetails = new CustomUserDetails(user);

        String accessToken = jwtService.generateToken(userDetails);

        return new LoginResponse(
                accessToken,
                refreshTokenEntity.getToken()
        );
    }

    @Override
    public void logout(String refreshToken) {

        RefreshToken refreshTokenEntity = refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Refresh token not found"));

        refreshTokenEntity.setRevoked(true);

        refreshTokenRepository.save(refreshTokenEntity);
    }
}
