package com.realtime.auction.sharedlib.dto;

public enum UserRole {
    ROLE_BIDDER, ROLE_SELLER, ROLE_USER, ROLE_ADMIN;

    public String toAuthority() {
        return this.name();
    }
}