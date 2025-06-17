package com.realtime.auction.auth_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenValidationRequest {
    @NotBlank
    private String token;
}
