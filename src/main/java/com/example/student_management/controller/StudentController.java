package com.example.student_management.controller;

import com.example.student_management.dto.ApiResponse;
import com.example.student_management.dto.StudentDTO.StudentRequest;
import com.example.student_management.dto.StudentDTO.StudentResponse;
import com.example.student_management.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ApiResponse<StudentResponse> create(
            @RequestBody StudentRequest request
    ) {
        return new ApiResponse<>(
                201,
                "Created",
                studentService.createStudent(request)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<StudentResponse> update(
            @PathVariable Long id,
            @RequestBody StudentRequest request
    ) {
        return new ApiResponse<>(
                200,
                "Updated",
                studentService.updateStudent(id, request)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentResponse> getById(
            @PathVariable Long id
    ) {
        return new ApiResponse<>(
                200,
                "Success",
                studentService.getStudentById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable Long id
    ) {
        studentService.deleteStudent(id);
        return new ApiResponse<>(200, "Deleted", null);
    }
}
