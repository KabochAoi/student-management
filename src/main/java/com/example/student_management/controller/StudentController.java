package com.example.student_management.controller;

import com.example.student_management.dto.ApiResponse;
import com.example.student_management.entity.Student;
import com.example.student_management.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ApiResponse<Student> createStudent(@RequestBody Student student) {
        return new ApiResponse<>(
                201,
                "Student created successfully",
                studentService.createStudent(student)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student
    ) {
        return new ApiResponse<>(
                200,
                "Student updated successfully",
                studentService.updateStudent(id, student)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<Student> getStudentById(@PathVariable Long id) {
        return new ApiResponse<>(
                200,
                "Get student successfully",
                studentService.getStudentById(id)
        );
    }

    @GetMapping
    public ApiResponse<List<Student>> getAllStudents() {
        return new ApiResponse<>(
                200,
                "Get all students successfully",
                studentService.getAllStudents()
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ApiResponse<>(
                200,
                "Student deleted successfully",
                null
        );
    }
}
