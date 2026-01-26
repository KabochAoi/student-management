package com.example.student_management.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int status;
    private String message;
    private T data;

    /* ================== STATIC HELPERS ================== */

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(200)
                .message("Success")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .status(200)
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResponse<Void> error(int status, String message) {
        return ApiResponse.<Void>builder()
                .status(status)
                .message(message)
                .data(null)
                .build();
    }
}
