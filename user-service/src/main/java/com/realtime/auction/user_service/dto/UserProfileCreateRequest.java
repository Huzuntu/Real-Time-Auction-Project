package com.realtime.auction.user_service.dto;


import com.realtime.auction.sharedlib.dto.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class UserProfileCreateRequest {
    private UUID userId;
    private String email;
    private String name;
    private UserRole userRole;
}