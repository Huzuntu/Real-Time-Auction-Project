package com.realtime.auction.user_service.mapper;

import com.realtime.auction.user_service.dto.UserProfileResponse;
import com.realtime.auction.user_service.model.UserProfile;

public class UserMapper {

    public static UserProfileResponse mapToResponse(UserProfile profile) {
        return UserProfileResponse.builder()
                .id(profile.getId())
                .name(profile.getName())
                .balance(profile.getBalance())
                .roles(profile.getRoles())
                .createdAt(profile.getCreatedAt())
                .build();
    }
}
