package com.example.student_management.service;

import com.example.student_management.entity.Student;

import java.util.List;

public interface StudentService {

    Student createStudent(Student student);

    Student updateStudent(Long id, Student student);

    Student getStudentById(Long id);

    List<Student> getAllStudents();

    void deleteStudent(Long id);
}
