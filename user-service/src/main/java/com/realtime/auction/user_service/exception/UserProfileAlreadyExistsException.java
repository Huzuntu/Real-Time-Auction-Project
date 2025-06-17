package com.realtime.auction.user_service.exception;

public class UserProfileAlreadyExistsException extends RuntimeException {
    public UserProfileAlreadyExistsException(String message) {
        super(message);
    }
}
