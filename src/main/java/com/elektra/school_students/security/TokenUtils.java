package com.elektra.school_students.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Collections;

public class TokenUtils {
    private final static SecretKey ACCESS_TOKEN_SECRET = Jwts.SIG.HS256.key().build();
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 86400L; // Días * 24 Horas * 60 Minutos * 60 Segundos

    public static String createToken(String nombre, String email) {
        Long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        return Jwts.builder()
                .subject(email)
                .claim("nombre", nombre)
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(ACCESS_TOKEN_SECRET)
                .compact();
    }

    public static UsernamePasswordAuthenticationToken validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(ACCESS_TOKEN_SECRET)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String email = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (JwtException e) {
            return null;
        }
    }
}
