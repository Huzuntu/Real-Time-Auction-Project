package com.realtime.auction.user_service.dto;

import com.realtime.auction.sharedlib.dto.UserRole;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
public class UserProfileResponse {
    UUID id;
    String name;
    BigDecimal balance;
    Set<UserRole> roles;
    Instant createdAt;
}

