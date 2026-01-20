package com.example.student_management.service;

import com.example.student_management.dto.ApiResponse;
import com.example.student_management.dto.AuthDTO.JwtResponse;
import com.example.student_management.dto.AuthDTO.LoginRequest;
import com.example.student_management.dto.AuthDTO.RegisterRequest;

public interface AuthService {

    ApiResponse<JwtResponse> login(LoginRequest request);

    ApiResponse<?> register(RegisterRequest request);
}
