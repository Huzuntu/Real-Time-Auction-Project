package com.realtime.auction.auth_service.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "token_blacklist")
@Data
@Builder
public class TokenBlacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant blacklistedAt;
}