package com.chrisp1985.cockroachdemo.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingParams(MissingServletRequestParameterException ex) {
        log.warn("Request received, missing required parameter: {}", ex.getParameterName());
        Map<String, String> error = new HashMap<>();
        error.put("error", "Missing required parameter: " + ex.getParameterName());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> conflict(DataIntegrityViolationException ex) {
        log.warn("Request received, data integrity exception: {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("error", "Data Invalid. Please check the request fields and format and try again.");
        return ResponseEntity.badRequest().body(error);
    }
}