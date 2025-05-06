package com.raji.todo.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long accessTokenValidityMs = 15L * 60L * 1000L;
   private long refreshTokenValidityMs = 30L * 60L * 1000L;


    public JwtService(@Value("${jwt.secret}") String secret) {
        secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret.getBytes()));
    }

    private String generateToken(String userId, String type, long expiry) {
        return Jwts.builder()
                .subject(userId)
                .claim("type", type)
                .issuedAt(java.util.Date.from(java.time.Instant.now()))
                .expiration(java.util.Date.from(java.time.Instant.now().plusMillis(expiry)))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();

    }

    public String generateAccessToken(String userId) {
        return generateToken(userId, "access", accessTokenValidityMs);
    }

    public String generateRefreshToken(String userId) {
        return generateToken(userId, "refresh", refreshTokenValidityMs);
    }

    public boolean validateAccessToken(String token) {
        Claims claims = parseClaims(token);
        return claims.get("type").equals("access");
    }

    public boolean validateRefreshToken(String token) {
        Claims claims = parseClaims(token);
        return claims.get("type").equals("refresh");
    }

    public String getUserIdFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.replaceAll("Bearer ", "").trim();
        }
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }
}
