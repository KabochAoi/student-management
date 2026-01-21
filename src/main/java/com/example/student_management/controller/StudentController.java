package com.example.student_management.controller;

import com.example.student_management.dto.ApiResponse;
import com.example.student_management.dto.StudentDTO.StudentRequest;
import com.example.student_management.dto.StudentDTO.StudentResponse;
import com.example.student_management.entity.Student;
import com.example.student_management.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // CREATE
    @PostMapping
    public ApiResponse<StudentResponse> createStudent(
            @RequestBody StudentRequest request) {

        Student student = studentService.createStudent(request);

        return new ApiResponse<>(
                201,
                "Student created successfully",
                mapToResponse(student)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ApiResponse<StudentResponse> updateStudent(
            @PathVariable Long id,
            @RequestBody StudentRequest request) {

        Student student = studentService.updateStudent(id, request);

        return new ApiResponse<>(
                200,
                "Student updated successfully",
                mapToResponse(student)
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ApiResponse<StudentResponse> getStudentById(@PathVariable Long id) {

        Student student = studentService.getStudentById(id);

        return new ApiResponse<>(
                200,
                "Get student successfully",
                mapToResponse(student)
        );
    }

    // GET ALL
    @GetMapping
    public ApiResponse<List<StudentResponse>> getAllStudents() {

        List<StudentResponse> responses = studentService.getAllStudents()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new ApiResponse<>(
                200,
                "Get all students successfully",
                responses
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ApiResponse<>(
                200,
                "Student deleted successfully",
                null
        );
    }

    // ================== MAPPER ==================
    private StudentResponse mapToResponse(Student student) {
        StudentResponse res = new StudentResponse();

        res.setId(student.getId());
        res.setStudentCode(student.getStudentCode());
        res.setFullName(student.getFullName());
        res.setEmail(student.getEmail());
        res.setPhone(student.getPhone());
        res.setDateOfBirth(student.getDateOfBirth());
        res.setGender(student.getGender());
        res.setAddress(student.getAddress());
        res.setClassName(student.getClassName());
        res.setGpa(student.getGpa());
        res.setStatus(student.getStatus());
        res.setCreatedAt(student.getCreatedAt());

        if (student.getCreatedBy() != null) {
            res.setCreatedByUsername(student.getCreatedBy().getUsername());
        }

        return res;
    }
}
