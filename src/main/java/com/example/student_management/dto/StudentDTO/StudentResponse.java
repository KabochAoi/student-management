package com.example.student_management.dto.StudentDTO;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse implements Serializable {

    private static final long serialVersionUID = 1L;

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
}
