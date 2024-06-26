package com.marcosrod.authentication.config.security.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String generateToken(Long userId, String userName, Collection<? extends GrantedAuthority> authorities);
    String extractUsername(String token);
    Date extractExpiration(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    Boolean validateToken(String token, UserDetails userDetails);
}
