package com.example.student_management.dto.AuthDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String email;
    private String fullName;
    private String password;
}
