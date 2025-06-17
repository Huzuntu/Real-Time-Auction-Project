package com.realtime.auction.payment_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "auction_id", nullable = false)
    private UUID auctionId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(name = "transaction_time")
    private Instant transactionTime;

    @Column(name = "retry_count")
    private int retryCount = 0;
}
