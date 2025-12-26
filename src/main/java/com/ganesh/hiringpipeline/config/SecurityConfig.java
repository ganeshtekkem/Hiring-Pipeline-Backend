package com.ganesh.hiringpipeline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import com.ganesh.hiringpipeline.security.*;
@Configuration
public class SecurityConfig {

    private final JwtService jwtService;
    
    public SecurityConfig(JwtService jwtservice){
        this.jwtService = jwtservice;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/auth/**",
                "/health",
                "/users"          // allow POST /users (registration)
            ).permitAll()
            .anyRequest().authenticated())
            .addFilterBefore(
                new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
            

        return http.build();
    }
}
