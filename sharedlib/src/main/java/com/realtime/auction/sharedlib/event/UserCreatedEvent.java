package com.realtime.auction.sharedlib.event;

import com.realtime.auction.sharedlib.dto.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent {
    private UUID userId;
    private String name;
    private String email;
    private Set<UserRole> roles;
    private Instant createdAt;
}