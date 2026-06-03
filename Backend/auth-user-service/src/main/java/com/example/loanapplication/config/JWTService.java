package com.example.loanapplication.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JWTService {
    private final String SECRET_KEY = "my-secret-key-my-secret-key-my-secret-key";
    private final long ACCESS_EXPIRATION = 1000 * 60 * 60 * 8; // 60 min
//private final long ACCESS_EXPIRATION = 30000; //30 sec.
//private final long ACCESS_EXPIRATION = 1000 * 60 * 2;
    private final long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 days

    public String generateAccessToken(String userID, String role) {
        return Jwts.builder()
                .setSubject(userID)
                .setIssuedAt(new Date())
                .claim("role",role)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
                .claim("type", "access")
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String userID,String role) {
        return Jwts.builder()
                .setSubject(userID)
                .setIssuedAt(new Date())
                .claim("role",role)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                .claim("type", "refresh")
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

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
