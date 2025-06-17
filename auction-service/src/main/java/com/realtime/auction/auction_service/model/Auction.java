package com.realtime.auction.auction_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name = "auctions")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Positive
    @Column(name = "start_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal startPrice;

    @Positive
    @Column(name = "min_increment", precision = 10, scale = 2, nullable = false)
    private BigDecimal minIncrement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AuctionStatus status = AuctionStatus.PENDING;

    @Column(name = "created_by", nullable = false, columnDefinition = "UUID")
    private UUID createdBy;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @Column(name = "winner_id", columnDefinition = "UUID")
    private UUID winnerId;
}
