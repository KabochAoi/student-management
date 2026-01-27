package com.example.student_management.service;

import com.example.student_management.dto.StudentDTO.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisStudentCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String STUDENT_KEY = "student::";
    private static final String STUDENTS_KEY = "students";

    private static final Duration TTL = Duration.ofMinutes(10);

    // ===== SINGLE =====
    public StudentResponse getStudent(Long id) {
        return (StudentResponse) redisTemplate.opsForValue()
                .get(STUDENT_KEY + id);
    }

    public void cacheStudent(StudentResponse response) {
        redisTemplate.opsForValue()
                .set(STUDENT_KEY + response.getId(), response, TTL);
    }

    public void evictStudent(Long id) {
        redisTemplate.delete(STUDENT_KEY + id);
    }

    // ===== LIST =====
    public List<StudentResponse> getAllStudents() {
        return (List<StudentResponse>) redisTemplate.opsForValue()
                .get(STUDENTS_KEY);
    }

    public void cacheAllStudents(List<StudentResponse> students) {
        redisTemplate.opsForValue()
                .set(STUDENTS_KEY, students, TTL);
    }

    public void evictAllStudents() {
        redisTemplate.delete(STUDENTS_KEY);
    }
}
