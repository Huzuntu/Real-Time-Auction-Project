package com.realtime.auction.auth_service.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest (
    @NotBlank String email,
    @NotBlank String password
) {}
