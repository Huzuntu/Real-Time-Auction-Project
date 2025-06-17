package com.realtime.auction.auth_service.repository;

import com.realtime.auction.auth_service.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<UserAuth, UUID> {
    boolean existsByEmail(String email);
    Optional<UserAuth> findByEmail(String email);

}
