package com.realtime.auction.auth_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

//@FeignClient(name = "user-service", url = "${user.service.url}")
//public interface UserServiceClient {
//    @PostMapping("/api/users")
//    void createUserProfile(@RequestBody UserProfileCreateRequest request,
//                           @RequestHeader("Authorization") String token);
//}