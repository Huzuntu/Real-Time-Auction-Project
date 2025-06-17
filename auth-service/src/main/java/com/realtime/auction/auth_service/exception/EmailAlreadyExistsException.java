package com.realtime.auction.auth_service.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super("Email " + message + " is already registered");
    }
}
