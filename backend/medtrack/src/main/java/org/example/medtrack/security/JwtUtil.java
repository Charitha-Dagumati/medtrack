package org.example.medtrack.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // ⚠️ Secret must be at least 32 characters
    private static final String SECRET =
            "medtrack_super_secure_secret_key_123456789";

    private final Key key =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    // =========================
    // GENERATE TOKEN (WITH ROLE)
    // =========================
    public String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)   // USER / ADMIN
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60) // 1 hour
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // =========================
    // EXTRACT USERNAME
    // =========================
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // =========================
    // EXTRACT ROLE
    // =========================
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // =========================
    // VALIDATE TOKEN
    // =========================
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // PARSE TOKEN CLAIMS
    // =========================
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}