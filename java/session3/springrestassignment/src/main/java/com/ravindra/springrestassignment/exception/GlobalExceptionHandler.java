package com.ravindra.springrestassignment.exception;

import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        return "Error: " + ex.getMessage();
    }
}