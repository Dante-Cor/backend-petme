package com.petme.backend.security;

import com.petme.backend.model.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    // 1. Generar token con claims personalizados
    public String generateToken(User user) {
        long expiration = 1000L * 60 * 60 * 24 * 10; // 3 dias

        return Jwts.builder()
                .signWith(secretKey)
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("status", user.getStatus())
                .compact();
    }

    // 2. Parsear el token y devolver los claims
    public Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }

    // 3. Extraer el username (subject)
    public String extractUsername(String token) {
        return parseToken(token).getPayload().getSubject();
    }

    // 4. Extraer claims personalizados
    public Long extractUserId(String token) {
        return parseToken(token).getPayload().get("id", Long.class);
    }

    public String extractEmail(String token) {
        return parseToken(token).getPayload().get("email", String.class);
    }

    public Boolean extractStatus(String token) {
        return parseToken(token).getPayload().get("status", Boolean.class);
    }

    // 5. Validar token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = parseToken(token).getPayload().getExpiration();
        return expiration.before(new Date());
    }
}