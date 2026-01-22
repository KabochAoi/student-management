package com.example.student_management.config;

import com.example.student_management.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // AUTH
                        .requestMatchers("/api/auth/**").permitAll()

                        // SWAGGER
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // STUDENT API
                        .requestMatchers(HttpMethod.DELETE, "/api/students/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/students/**")
                        .hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.POST, "/api/students")
                        .hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.GET, "/api/students/**")
                        .hasAnyRole("ADMIN", "USER")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
