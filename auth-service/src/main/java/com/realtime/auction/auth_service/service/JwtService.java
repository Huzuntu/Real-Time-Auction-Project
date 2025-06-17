package com.realtime.auction.auth_service.service;

import com.realtime.auction.sharedlib.dto.UserRole;
import com.realtime.auction.auth_service.model.UserAuth;
import com.realtime.auction.auth_service.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long accessTokenExpiration;

    public String generateAccessToken(UserAuth user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles().stream()
                .map(UserRole::name)
                .collect(Collectors.toList()));
        // claims.put("roles", roles.stream().map(Enum::name).toList());
        claims.put("userId", user.getId().toString());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(user.getId().toString())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
//                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
//                .compact();

    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = parseToken(token).getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public UUID getUserIdFromToken(String token) {
        String userIdStr = parseToken(token).getSubject();
        return UUID.fromString(userIdStr);
    }

    public String extractUsername(String token) {
        return parseToken(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return parseToken(token).getExpiration();
    }

    public Set<String> extractRoles(String token) {
        Claims claims = parseToken(token);
        String rolesStr = claims.get("roles", String.class);
        if (rolesStr != null) {
            return Arrays.stream(rolesStr.split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }


    public Instant getTokenExpiration(String token) {
        return extractExpiration(token).toInstant();
    }

    public boolean isTokenValid(String token, String userId) {
        try {
            String tokenUserId = extractUsername(token);
            return tokenUserId.equals(userId) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public String generateToken(String userId, Set<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", String.join(",", roles));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseToken(token);
        return claimsResolver.apply(claims);
    }
}