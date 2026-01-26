package com.example.student_management.dto.AuthDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;
    private String username;
    private List<String> roles;
}
