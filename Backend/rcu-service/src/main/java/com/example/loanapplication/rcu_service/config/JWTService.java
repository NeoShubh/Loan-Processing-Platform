package com.example.loanapplication.rcu_service.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JWTService {
    private final String SECRET_KEY = "my-secret-key-my-secret-key-my-secret-key";
    private final long ACCESS_EXPIRATION = 1000 * 60 * 60 * 8; // 60 min
    //private final long ACCESS_EXPIRATION = 30000; //30 sec.
//private final long ACCESS_EXPIRATION = 1000 * 60 * 2;
    private final long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 days


    private Key getSignInKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //    This method checks: Signature Expiration Structure
    public String extractTokenType(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("type", String.class);
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
