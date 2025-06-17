package com.realtime.auction.user_service.exception;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ErrorResponse {
    private String message;
    private Instant timestamp;
}
