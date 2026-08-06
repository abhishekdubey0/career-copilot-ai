package com.careercopilot.auth.common;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ResponseBuilder {

    private ResponseBuilder() {
    }

    public static <T> ApiResponse<T> success(
            HttpStatus status,
            String message,
            T data) {

        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(message)
                .data(data)
                .build();
    }
}