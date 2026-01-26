package com.example.student_management.config;

import com.example.student_management.entity.Role;
import com.example.student_management.entity.User;
import com.example.student_management.repository.RoleRepository;
import com.example.student_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData(
            UserRepository userRepository,
            RoleRepository roleRepository
    ) {
        return args -> {

            // ================== ROLES ==================

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName("ROLE_ADMIN");
                        return roleRepository.save(role);
                    });

            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName("ROLE_USER");
                        return roleRepository.save(role);
                    });

            // ================== ADMIN USER ==================

            if (userRepository.findByUsername("admin").isEmpty()) {

                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                roles.add(userRole);

                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setFullName("Administrator");
                admin.setEmail("admin@gmail.com");
                admin.setRoles(roles);
                admin.setEnabled(true);

                userRepository.save(admin);
            }
        };
    }
}
