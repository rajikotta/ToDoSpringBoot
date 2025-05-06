package com.raji.todo.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.append(error.getDefaultMessage()).append(","));
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("errors", errors.toString());
        return ResponseEntity.badRequest().body(errorsMap);
    }
}
