package com.example.student_management.service;

import com.example.student_management.dto.ApiResponse;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // ================= LOGIN =================
    @Override
    public ApiResponse<JwtResponse> login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        User user = (User) authentication.getPrincipal();

        String token = jwtService.generateToken(user);

        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        JwtResponse response = new JwtResponse(
                token,
                user.getUsername(),
                roles
        );

        return new ApiResponse<>(
                200,
                "Login successful",
                response
        );
    }

    // ================= REGISTER =================
    @Override
    public ApiResponse<?> register(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return new ApiResponse<>(
                    400,
                    "Username already exists",
                    null
            );
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new ApiResponse<>(
                    400,
                    "Email already exists",
                    null
            );
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() ->
                        new RuntimeException("ROLE_USER not found")
                );

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setRoles(List.of(userRole).stream().collect(Collectors.toSet()));
        user.setEnabled(true);

        userRepository.save(user);

        return new ApiResponse<>(
                201,
                "Register successful",
                null
        );
    }
}
