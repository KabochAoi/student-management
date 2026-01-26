package com.example.student_management.service;

import com.example.student_management.dto.StudentDTO.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisStudentCacheService {

    private static final String STUDENT_LIST_KEY = "students:all";
    private static final String STUDENT_KEY_PREFIX = "students:";

    private final RedisTemplate<String, Object> redisTemplate;

    // ===== GET =====
    public StudentResponse getStudent(Long id) {
        return (StudentResponse)
                redisTemplate.opsForValue()
                        .get(STUDENT_KEY_PREFIX + id);
    }

    public List<StudentResponse> getAllStudents() {
        return (List<StudentResponse>)
                redisTemplate.opsForValue()
                        .get(STUDENT_LIST_KEY);
    }

    // ===== PUT =====
    public void cacheStudent(StudentResponse student) {
        redisTemplate.opsForValue()
                .set(
                        STUDENT_KEY_PREFIX + student.getId(),
                        student,
                        10,
                        TimeUnit.MINUTES
                );
    }

    public void cacheAllStudents(List<StudentResponse> students) {
        redisTemplate.opsForValue()
                .set(
                        STUDENT_LIST_KEY,
                        students,
                        10,
                        TimeUnit.MINUTES
                );
    }

    // ===== EVICT =====
    public void evictStudent(Long id) {
        redisTemplate.delete(STUDENT_KEY_PREFIX + id);
    }

    public void evictAllStudents() {
        redisTemplate.delete(STUDENT_LIST_KEY);
    }
}
