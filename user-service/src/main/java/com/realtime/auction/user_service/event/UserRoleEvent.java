package com.realtime.auction.user_service.event;

import com.realtime.auction.sharedlib.dto.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UserRoleEvent {
    UUID userId;
    UserRole role;
    Instant timestamp;
}