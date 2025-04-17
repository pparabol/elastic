package ru.test.elastic.exception;

import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.lang.String.format;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<ApiError> handleNotFoundException(final NotFoundException e) {
        log.warn(format("%s was thrown, cause: %s", e.getClass().getName(), e.getMessage()));
       return ResponseEntity.notFound().build();
    }

    @Value
    @Builder
    private static class ApiError {
        String message;
    }
}
