package com.realtime.auction.auth_service.event;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class LoginEvent {
    private UUID userId;
    private String email;
    private Instant loginAt;
}
