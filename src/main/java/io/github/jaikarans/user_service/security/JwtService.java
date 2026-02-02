package io.github.jaikarans.user_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key;
    private final long expiration;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration) {

        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration;
    }

    // GENERATE JWT TOKEN
    public String generateToken(String email, Long userId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("uid", userId)
                .claim("role", "USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key) // Verify the token signature with the secret key
                .build()
                .parseSignedClaims(token) // Parse the claims
                .getPayload(); // Get the claims body
    }


    public Long extractUidFromJwt(String token) {
        return extractAllClaims(token).get("uid", Long.class);
    }

    public String extractEmailFromJwt(String token) {
        return extractAllClaims(token).getSubject();
    }




}
