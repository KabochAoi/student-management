package com.example.student_management.dto.StudentDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Student code is required")
    @Size(max = 50, message = "Student code must be <= 50 characters")
    private String studentCode;

    @NotBlank(message = "Full name is required")
    @Size(max = 100)
    private String fullName;

    @Email(message = "Email is invalid")
    @Size(max = 100)
    private String email;

    @Size(max = 20)
    private String phone;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Size(max = 10)
    private String gender;

    @Size(max = 255)
    private String address;

    @Size(max = 50)
    private String className;

    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "4.0", inclusive = true)
    private Double gpa;

    @Size(max = 20)
    private String status;
}
