package com.example.student_management.dto.AuthDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String username;
    private List<String> roles;
}
