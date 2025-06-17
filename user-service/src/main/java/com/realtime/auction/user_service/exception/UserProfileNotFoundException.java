package com.realtime.auction.user_service.exception;

import java.util.UUID;


public class UserProfileNotFoundException extends RuntimeException {
    public UserProfileNotFoundException(String message) {
        super(message);
    }
}