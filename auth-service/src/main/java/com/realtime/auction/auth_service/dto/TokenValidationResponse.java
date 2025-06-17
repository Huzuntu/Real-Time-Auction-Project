package com.realtime.auction.auth_service.dto;

import com.realtime.auction.sharedlib.dto.UserRole;
import lombok.Builder;
import lombok.Data;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
public class TokenValidationResponse {
    private boolean valid;
    private UUID userId;
    private String email;
    private Set<UserRole> roles;
    private Instant expiresAt;
}
