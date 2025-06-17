package com.realtime.auction.user_service.service;

import com.realtime.auction.user_service.dto.UserProfileCreateRequest;
import com.realtime.auction.user_service.dto.UserProfileResponse;
import com.realtime.auction.sharedlib.event.UserCreatedEvent;
import com.realtime.auction.user_service.event.UserRoleEvent;
import com.realtime.auction.user_service.exception.UserProfileNotFoundException;
import com.realtime.auction.user_service.model.UserProfile;
import com.realtime.auction.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, UserRoleEvent> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserProfileResponse getUserProfile(UUID userId) {
        UserProfile user = userRepository.findById(userId)
                .orElseThrow(() -> new UserProfileNotFoundException("User Profile Not Found"));

        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .balance(user.getBalance())
                .roles(user.getRoles())
                .createdAt(user.getCreatedAt())
                .build();
    }


//    private void publishRoleUpdateEvent(UUID userId, Set<String> roles) {
//        UserRoleEvent event = UserRoleEvent.builder()
//                .userId(userId)
//                .roles(roles)
//                .timestamp(Instant.now())
//                .build();
//
//        kafkaTemplate.send("user-role-updates", userId.toString(), event);
//    }
//
//    @Transactional
//    public UserProfileResponse createUserProfile(UserProfileCreateRequest request) throws UserProfileAlreadyExistsException {
//        if (userRepository.existsById(request.getUserId())) {
//            throw new UserProfileAlreadyExistsException("User already exists");
//        }
//
//        UserProfile userProfile = UserProfile.builder()
//                .id(request.getUserId())
//                .name(request.getName())
//                .balance(BigDecimal.ZERO)
//                .role(request.getUserRole())
//                .createdAt(Instant.now())
//                .build();
//
//        UserProfile savedUser = userRepository.save(userProfile);
//
//        return UserProfileResponse.builder()
//                .id(savedUser.getId())
//                .name(savedUser.getName())
//                .balance(BigDecimal.ZERO)
//                .role(savedUser.getRole())
//                .createdAt(Instant.now())
//                .build();
//    }

    @Transactional
    public void createUserProfile(UserCreatedEvent event) {
        if (userRepository.existsById(event.getUserId())) {
            logger.warn("User already exists: {}", event.getUserId());
            return;
        }

        userRepository.save(
                UserProfile.builder()
                        .id(event.getUserId())
                        .name(event.getName())
                        .roles(event.getRoles())
                        .balance(BigDecimal.ZERO)
                        .createdAt(Instant.now())
                        .build()
        );

        logger.info("User profile created for: {}", event.getUserId());
    }
}
