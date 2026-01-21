package com.example.student_management.dto.StudentDTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentRequest {

    private String studentCode;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String className;
    private Double gpa;
    private String status;
}
