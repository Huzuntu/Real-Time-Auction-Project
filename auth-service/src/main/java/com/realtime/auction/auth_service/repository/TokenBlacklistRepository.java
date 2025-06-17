package com.realtime.auction.auth_service.repository;

import com.realtime.auction.auth_service.model.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, UUID> {
    Optional<TokenBlacklist> findByToken(String token);
    boolean existsByToken(String token);
}