package com.example.student_management.dto.AuthDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.spi.Tokens;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String username;
    private List<String> roles;

    public static Tokens builder() {
    }
}
