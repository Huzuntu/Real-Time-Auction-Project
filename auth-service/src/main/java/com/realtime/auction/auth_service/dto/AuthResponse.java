package com.realtime.auction.auth_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private Instant expiresAt;
    private UserAuthInfo userInfo;

    @Builder.Default
    private String tokenType = "Bearer";
}
