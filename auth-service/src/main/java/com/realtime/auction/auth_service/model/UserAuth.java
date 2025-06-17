package com.realtime.auction.auth_service.model;

import com.realtime.auction.sharedlib.dto.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "auth_users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "uk_auth_users_email")
})
@Builder
@Data
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Email
    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 512, name = "password_hash")
    private String passwordHash;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "auth_user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Builder.Default
    private Set<UserRole> roles = new HashSet<>(Set.of(UserRole.ROLE_USER));

    @Builder.Default
    @Column(name = "is_enabled", nullable = false)
    private boolean enabled = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    private Instant lastLoginAt;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(UserRole::toAuthority)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
