package com.example.student_management.service;

import com.example.student_management.dto.*;
import com.example.student_management.dto.AuthDTO.JwtResponse;
import com.example.student_management.dto.AuthDTO.LoginRequest;
import com.example.student_management.dto.AuthDTO.RegisterRequest;
import com.example.student_management.entity.Role;
import com.example.student_management.entity.User;
import com.example.student_management.repository.RoleRepository;
import com.example.student_management.repository.UserRepository;
import com.example.student_management.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public ApiResponse<JwtResponse> login(LoginRequest request) {
        return null;
    }

    @Override
    public ApiResponse<?> register(RegisterRequest request) {
        return null;
    }

    @Override
    public ApiResponse<AuthDTO.JwtResponse> login(AuthDTO.LoginRequest req) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );

        User user = (User) auth.getPrincipal();

        String token = jwtService.generateToken(user);

        return new ApiResponse<>(200, "OK",
                new AuthDTO.JwtResponse(token, user.getUsername(), null));
    }

    @Override
    public ApiResponse<?> register(AuthDTO.RegisterRequest req) {

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow();

        User u = new User();
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setFullName(req.getFullName());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRoles(Collections.singleton(role));

        userRepository.save(u);

        return new ApiResponse<>(201, "REGISTER OK", null);
    }
}
