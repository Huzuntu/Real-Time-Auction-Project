package com.realtime.auction.auth_service.service;

import com.realtime.auction.auth_service.dto.*;
import com.realtime.auction.auth_service.event.LoginEvent;
import com.realtime.auction.auth_service.publisher.LoginEventPublisher;
import com.realtime.auction.sharedlib.dto.UserRole;
import com.realtime.auction.sharedlib.event.UserCreatedEvent;
import com.realtime.auction.auth_service.exception.EmailAlreadyExistsException;
import com.realtime.auction.auth_service.model.TokenBlacklist;
import com.realtime.auction.auth_service.model.UserAuth;
import com.realtime.auction.auth_service.publisher.UserCreateEventPublisher;
import com.realtime.auction.auth_service.repository.AuthRepository;
import com.realtime.auction.auth_service.repository.TokenBlacklistRepository;
import com.realtime.auction.auth_service.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserCreateEventPublisher userCreateEventPublisher;
    private final LoginEventPublisher loginEventPublisher;
    private final TokenBlacklistRepository tokenBlacklistRepository;
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        logger.info("Attempting to register user with email: {}", request.getEmail());

        if (authRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + request.getEmail());
        }

        UserAuth savedUserAuth = authRepository.save(UserAuth.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(UserRole.ROLE_USER))
                .enabled(true)
                .createdAt(Instant.now())
                .build());

        String accessToken = jwtService.generateAccessToken(savedUserAuth);

        userCreateEventPublisher.publish(
                UserCreatedEvent.builder()
                        .userId(savedUserAuth.getId())
                        .name(request.getName())
                        .email(savedUserAuth.getEmail())
                        .roles(savedUserAuth.getRoles())
                        .createdAt(savedUserAuth.getCreatedAt())
                        .build()
        );

        UserAuthInfo userAuthInfo = UserAuthInfo.builder()
                .userId(savedUserAuth.getId())
                .email(request.getEmail())
                .build();

        return AuthResponse.builder()
                .accessToken(accessToken)
                .expiresAt(jwtService.getTokenExpiration(accessToken))
                .userInfo(userAuthInfo)
                .build();
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserAuth user = userDetails.getUser();

        Instant now = Instant.now();
        user.setLastLoginAt(now);
        UserAuth savedUser = authRepository.save(user);

        String accessToken = jwtService.generateAccessToken(savedUser);

        loginEventPublisher.publish(LoginEvent.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .loginAt(now)
                .build());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .userInfo(new UserAuthInfo(savedUser.getId(), savedUser.getEmail()))
                .expiresAt(jwtService.getTokenExpiration(accessToken))
                .build();
    }

    @Transactional
    public void logout(String token) {
        if (tokenBlacklistRepository.existsByToken(token)) {
            logger.warn("Token already blacklisted.");
            return;
        }
        try {
            UUID userId = jwtService.getUserIdFromToken(token);

            tokenBlacklistRepository.save(TokenBlacklist.builder()
                    .token(token)
                    .blacklistedAt(Instant.now())
                    .build());

            logger.info("User with ID {} logged out successfully", userId);
        } catch (Exception e) {
            logger.error("Failed to extract user ID from token during logout", e);
        }
    }

    @Transactional
    public TokenValidationResponse validateToken(String token) {
        boolean isBlacklisted = tokenBlacklistRepository.existsByToken(token);
        if (isBlacklisted) {
            return TokenValidationResponse.builder()
                    .valid(false)
                    .build();
        }

        boolean valid = jwtService.validateToken(token);
        if (!valid) {
            return TokenValidationResponse.builder()
                    .valid(false)
                    .build();
        }

        UUID userId = jwtService.getUserIdFromToken(token);
        UserAuth user = authRepository.findById(userId).orElse(null);
        if (user == null) {
            return TokenValidationResponse.builder()
                    .valid(false)
                    .build();
        }
        return TokenValidationResponse.builder()
                .valid(true)
                .userId(user.getId())
                .email(user.getEmail())
                .roles(user.getRoles())
                .expiresAt(jwtService.getTokenExpiration(token))
                .build();
    }

}
