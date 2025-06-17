package com.realtime.auction.user_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class RoleUpdateRequest {
        @NotEmpty
        Set<@Pattern(regexp = "ROLE_[A-Z]+") String> roles;
}
