package com.sweetshop.sweetshop_management.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 1 day

    // Expose key for parsing
    public static Key getKey() {
        return key;
    }

    public static String generateToken(String username, String role) {
    String prefixedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
    return Jwts.builder()
            .setSubject(username)
            .claim("role", prefixedRole)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key)
            .compact();
}

}
