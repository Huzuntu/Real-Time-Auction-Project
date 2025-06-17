package com.realtime.auction.user_service.controller;

import com.realtime.auction.user_service.dto.UserProfileResponse;
import com.realtime.auction.user_service.exception.UserProfileNotFoundException;
import com.realtime.auction.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok(userService.getUserProfile(userId));
        } catch (UserProfileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
//
//    @PostMapping
//    public ResponseEntity<UserProfileResponse> createUserProfile(
//            @Valid @RequestBody UserProfileCreateRequest request,
//            @RequestHeader("Authorization") String token) {
//        try {
//            UserProfileResponse response = userService.createUserProfile(request);
//
//            logger.info("User profile created successfully for user: {}", request.getUserId());
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
//
//        } catch (Exception e) {
//            logger.error("Failed to create user profile: {}", e.getMessage());
//            return ResponseEntity.badRequest().build();
//        }
//    }
}
