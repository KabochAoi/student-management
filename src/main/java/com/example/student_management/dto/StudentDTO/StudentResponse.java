package com.example.student_management.dto.StudentDTO;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class StudentResponse implements Serializable {

    private Long id;

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

    private LocalDateTime createdAt;
    private String createdBy;
}
