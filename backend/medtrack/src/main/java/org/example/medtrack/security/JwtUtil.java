package org.example.medtrack.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${JWT_SECRET:mysupersecretkeymysupersecretkey123456}")
    private String secret;

    private Key getSigningKey() {
        if (secret == null || secret.trim().length() < 32) {
            throw new IllegalStateException("JWT_SECRET is missing or too weak (min 32 chars required)");
        }

        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // GENERATE TOKEN
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // EXTRACT USERNAME
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // EXTRACT ROLE
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // VALIDATE TOKEN
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // PARSE CLAIMS
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}