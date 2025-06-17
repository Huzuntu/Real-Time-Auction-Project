package com.realtime.auction.user_service.repository;

import com.realtime.auction.user_service.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserProfile, UUID> {

}
