package com.marcosrod.serviceorder.config.security;

import com.marcosrod.serviceorder.modules.user.enums.Role;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.jwt-secret}")
    private String jwtKey;
    private static final String API_URI = "/api/orders";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, API_URI)
                        .hasAuthority(Role.R.getAuthority())
                        .requestMatchers(HttpMethod.PUT, API_URI)
                        .hasAuthority(Role.T.getAuthority())
                        .requestMatchers(HttpMethod.GET, API_URI + "/{id}/pending")
                        .hasAuthority(Role.T.getAuthority())
                        .requestMatchers(HttpMethod.GET, API_URI + "/{id}/progress", API_URI + "/report")
                        .hasAuthority(Role.R.getAuthority())
                        .anyRequest()
                        .authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
                    jwt.decoder(jwtDecoder()).jwtAuthenticationConverter(jwtAuthenticationConverter());
                }));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] secretKeyBytes = Decoders.BASE64.decode(jwtKey);

        return NimbusJwtDecoder
                .withSecretKey(Keys.hmacShaKeyFor(secretKeyBytes))
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new CustomJwtGrantedAuthoritiesConverter());

        return converter;
    }
}
