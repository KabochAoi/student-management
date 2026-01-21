package com.example.student_management.security;

import com.example.student_management.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateToken(User user);

    String extractUsername(String token);

    boolean isTokenValid(String token, User user);

    boolean validate(String token, UserDetails userDetails);
}
