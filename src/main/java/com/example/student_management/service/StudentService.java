package com.example.student_management.service;

import com.example.student_management.dto.StudentDTO.StudentRequest;
import com.example.student_management.entity.Student;

import java.util.List;

public interface StudentService {

    Student createStudent(StudentRequest request);

    Student updateStudent(Long id, StudentRequest request);

    Student getStudentById(Long id);

    List<Student> getAllStudents();

    void deleteStudent(Long id);
}
