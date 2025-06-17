package com.realtime.auction.user_service.model;

import com.realtime.auction.sharedlib.dto.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user_profiles")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserProfile {
    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @NotBlank
    @Column(length = 100)
    private String name;

    @Builder.Default
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "profile_user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Builder.Default
    private Set<UserRole> roles = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}