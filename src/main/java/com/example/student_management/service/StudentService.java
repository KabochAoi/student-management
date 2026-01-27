package com.example.student_management.service;

import com.example.student_management.dto.StudentDTO.StudentRequest;
import com.example.student_management.dto.StudentDTO.StudentResponse;

import java.util.List;

public interface    StudentService {

    StudentResponse createStudent(StudentRequest request);

    StudentResponse updateStudent(Long id, StudentRequest request);

    StudentResponse getStudentById(Long id);

    List<StudentResponse> getAllStudents();

    void deleteStudent(Long id);
}
