package com.marcosrod.authentication.modules.authentication.helper;

import com.marcosrod.authentication.config.security.dto.UserAuthDetails;
import com.marcosrod.authentication.modules.authentication.dto.AuthRequest;
import com.marcosrod.authentication.modules.user.enums.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.marcosrod.authentication.modules.user.helper.UserHelper.getSavedUser;

public class AuthHelper {

    public static final String SECRET_KEY = "9D0EB6B1C2E1FAD0F53A248F6C3B5E4E2F6D8G3H1I0J7K4L1M9N2O3P5Q0R7S9T1U4V2W6X0Y2A";
    public static final String TEST_USER_EMAIL = "test@gmail.com";

    public static String getJwtToken(String email, String authority) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", Stream.of(new SimpleGrantedAuthority(authority))
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private static Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String getToken() {
        return "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImF1dGhvcml0aWVzIjpbIlJPTEVfUmVjZXB0aW9uaXN0Il0sInN1"
                + "YiI6InVzZXJSZWNlcEBleGFtcGxlLmNvbSIsImlhdCI6MTcxODgyNzAwMSwiZXhwIjoxNzE4ODI4ODAxfQ.svK"
                + "hDFMCfNwlixlZpFlfqM-eP4_FxNVRA9UElL2CHeM";
    }

    public static AuthRequest getAuthRequest() {
        return new AuthRequest("user@email.com", "userP");
    }

    public static UsernamePasswordAuthenticationToken getUserPasswordAuthToken() {
        return new UsernamePasswordAuthenticationToken(getAuthRequest().email(), getAuthRequest().password());
    }

    public static Collection<? extends GrantedAuthority> getUserAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.R.getDescription()));
    }

    public static UserDetails getUserDetails(Role role) {
        return new UserAuthDetails(getSavedUser(role));
    }
}
