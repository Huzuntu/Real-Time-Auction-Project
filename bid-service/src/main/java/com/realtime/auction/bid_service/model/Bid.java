package com.realtime.auction.bid_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "bids")
@Data
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "auction_id", nullable = false)
    private UUID auctionId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}
