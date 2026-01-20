package com.example.student_management.controller;

import com.example.student_management.dto.*;
import com.example.student_management.dto.AuthDTO.JwtResponse;
import com.example.student_management.dto.AuthDTO.LoginRequest;
import com.example.student_management.dto.AuthDTO.*;
import com.example.student_management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<JwtResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public ApiResponse<?> register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

}
