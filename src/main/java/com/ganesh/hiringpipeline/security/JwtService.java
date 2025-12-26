package com.ganesh.hiringpipeline.security;

import org.springframework.stereotype.Service;

import com.ganesh.hiringpipeline.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class JwtService {

    private static final String SECRET = "myselfsecretkeyforjwttokengeneration12345";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    public String generateToken(User user){
        return Jwts.builder()
            .setSubject(user.getEmail())
            .claim("role", user.getRole().getName())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key,SignatureAlgorithm.HS256)
            .compact();
    }

    public String extractEmail(String token){
        return getClaims(token).getSubject();
    }
    public boolean isTokenValid(String token){
        
        try{
            getClaims(token);
        return true;
        }catch (Exception e){
            return false;
        }
    }

    private Claims getClaims(String token){
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}
