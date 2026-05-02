package com.Capstone.InterviewTracking.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private List<String> errors;
    private LocalDateTime timestamp;
    // private int status;
    

    public ApiResponse(boolean success, String message, T data, List<String> errors ) {
        this.success = success;
        this.message = message;
        this.data = data;   
        this.errors = errors;
        // this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(String message, T data ) {
        return new ApiResponse<T>(true, message, data, List.of());
    }

    public static <T> ApiResponse<T> failure(String message ) {
        return new ApiResponse<T>(false, message, null, List.of() );
    }

    public static <T> ApiResponse<T> failure(String message, List<String> errors) {
        return new ApiResponse<T>(false, message, null, errors );
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
