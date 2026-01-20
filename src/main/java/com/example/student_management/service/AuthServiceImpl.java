package com.example.student_management.service;

import com.example.student_management.dto.ApiResponse;
import com.example.student_management.dto.AuthDTO.*;
import com.example.student_management.entity.Role;
import com.example.student_management.entity.User;
import com.example.student_management.repository.RoleRepository;
import com.example.student_management.repository.UserRepository;
import com.example.student_management.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    @Override
    public ApiResponse<JwtResponse> login(LoginRequest req) {

        var auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        User user = (User) auth.getPrincipal();

        String token = jwtService.generateToken(user);

        List<String> roles = user.getRoles().stream()
                .map(Role::getName).toList();

        return new ApiResponse<>(200, "Login success",
                new JwtResponse(token, user.getUsername(), roles));
    }

    @Override
    public ApiResponse<?> register(RegisterRequest req) {

        if (userRepo.existsByUsername(req.getUsername()))
            return new ApiResponse<>(400, "Username exists", null);

        Role role = roleRepo.findByName("ROLE_USER")
                .orElseThrow();

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setFullName(req.getFullName());
        user.setPassword(req.getPassword());
        user.setRoles(Set.of(role));

        userRepo.save(user);

        return new ApiResponse<>(201, "Register success", null);
    }
}
