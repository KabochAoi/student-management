package com.example.student_management.service;

import com.example.student_management.dto.StudentDTO.StudentRequest;
import com.example.student_management.entity.Student;
import com.example.student_management.entity.User;
import com.example.student_management.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    // CREATE
    @Override
    public Student createStudent(StudentRequest request) {

        Student student = new Student();

        student.setStudentCode(request.getStudentCode());
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setAddress(request.getAddress());
        student.setClassName(request.getClassName());
        student.setGpa(request.getGpa());
        student.setStatus(request.getStatus());
        student.setCreatedAt(LocalDateTime.now());

        // lấy user đang đăng nhập (admin)
        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        student.setCreatedBy(currentUser);

        return studentRepository.save(student);
    }

    // UPDATE
    @Override
    public Student updateStudent(Long id, StudentRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setAddress(request.getAddress());
        student.setClassName(request.getClassName());
        student.setGpa(request.getGpa());
        student.setStatus(request.getStatus());

        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
