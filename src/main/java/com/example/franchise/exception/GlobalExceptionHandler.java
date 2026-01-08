package com.example.franchise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return Mono.just(Map.of(
                "timestamp", Instant.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "error", ex.getMessage()
        ));
    }

    @ExceptionHandler(BusinessException.class)
    public Mono<Map<String, Object>> handleBusiness(BusinessException ex) {
        return Mono.just(Map.of(
                "timestamp", Instant.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", ex.getMessage()
        ));
    }
}
