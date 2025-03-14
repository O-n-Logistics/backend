package com.example.demo.global.presentation.dtos;

public record CommonResponse<T>(
    String message,
    T data
) {

    private static final String SUCCESS = "SUCCESS";

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(SUCCESS, data);
    }

    public static <T> CommonResponse<T> success() {
        return new CommonResponse<>(SUCCESS, null);
    }

    public static <T> CommonResponse<T> exception(String message) {
        return new CommonResponse<>(message, null);
    }
}