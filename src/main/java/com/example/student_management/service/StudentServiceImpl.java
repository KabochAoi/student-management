package com.example.student_management.service;

import com.example.student_management.dto.StudentDTO.StudentRequest;
import com.example.student_management.dto.StudentDTO.StudentResponse;
import com.example.student_management.entity.Student;
import com.example.student_management.entity.User;
import com.example.student_management.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final RedisStudentCacheService cacheService;

    // ===== CREATE =====
    @Override
    public StudentResponse createStudent(StudentRequest request) {

        Student student = new Student();
        mapRequestToEntity(request, student);

        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        student.setCreatedBy(currentUser);
        student.setCreatedAt(LocalDateTime.now());

        Student saved = studentRepository.save(student);

        cacheService.evictAllStudents();

        return mapEntityToResponse(saved);
    }

    // ===== UPDATE =====
    @Override
    public StudentResponse updateStudent(Long id, StudentRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        mapRequestToEntity(request, student);

        Student saved = studentRepository.save(student);

        cacheService.evictStudent(id);
        cacheService.evictAllStudents();

        return mapEntityToResponse(saved);
    }

    // ===== GET BY ID =====
    @Override
    public StudentResponse getStudentById(Long id) {

        StudentResponse cached = cacheService.getStudent(id);
        if (cached != null) return cached;

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentResponse response = mapEntityToResponse(student);
        cacheService.cacheStudent(response);

        return response;
    }

    // ===== GET ALL =====
    @Override
    @Cacheable(value = "students")
    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapEntityToResponse)
                .toList();
    }


    // ===== DELETE =====
    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
        cacheService.evictStudent(id);
        cacheService.evictAllStudents();
    }

    // ================== MAPPER ==================

    private void mapRequestToEntity(
            StudentRequest req,
            Student student
    ) {
        student.setStudentCode(req.getStudentCode());
        student.setFullName(req.getFullName());
        student.setEmail(req.getEmail());
        student.setPhone(req.getPhone());
        student.setDateOfBirth(req.getDateOfBirth());
        student.setGender(req.getGender());
        student.setAddress(req.getAddress());
        student.setClassName(req.getClassName());
        student.setGpa(req.getGpa());
        student.setStatus(req.getStatus());
    }

    private StudentResponse mapEntityToResponse(Student s) {
        return StudentResponse.builder()
                .id(s.getId())
                .studentCode(s.getStudentCode())
                .fullName(s.getFullName())
                .email(s.getEmail())
                .phone(s.getPhone())
                .dateOfBirth(s.getDateOfBirth())
                .gender(s.getGender())
                .address(s.getAddress())
                .className(s.getClassName())
                .gpa(s.getGpa())
                .status(s.getStatus())
                .createdAt(s.getCreatedAt())
                .createdBy(
                        s.getCreatedBy() != null
                                ? s.getCreatedBy().getUsername()
                                : null
                )
                .build();
    }
}
