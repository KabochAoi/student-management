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
    import java.util.Set;

    @RequiredArgsConstructor
    @Service
    public class AuthServiceImpl implements AuthService {

        private final UserRepository userRepo;
        private final RoleRepository roleRepo;
        private final AuthenticationManager authManager;
        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;

        @Override
        public ApiResponse<JwtResponse> login(LoginRequest request) {
            // 1. Authenticate username + password
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // 2. Lấy user đã authenticate
            User user = (User) authentication.getPrincipal();

            // 3. Generate JWT
            String token = jwtService.generateToken(user);

            // 4. Lấy danh sách role
            List<String> roles = user.getRoles()
                    .stream()
                    .map(Role::getName)
                    .toList();

            // 5. Trả response
            return new ApiResponse<>(
                    200,
                    "Login success",
                    new JwtResponse(token, user.getUsername(), roles)
            );
        }

        @Override
        public ApiResponse<?> register(RegisterRequest req) {

            if (userRepo.existsByUsername(req.getUsername()))
                return new ApiResponse<>(400, "Username exists", null);

            Role role = roleRepo.findByName("ROLE_USER").orElseThrow();

            User user = User.builder()
                    .username(req.getUsername())
                    .email(req.getEmail())
                    .fullName(req.getFullName())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .roles(Set.of(role))
                    .enabled(true)
                    .build();

            userRepo.save(user);

            return new ApiResponse<>(201, "Register success", null);
        }
    }
