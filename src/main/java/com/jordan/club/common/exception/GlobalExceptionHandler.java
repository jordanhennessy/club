package com.jordan.club.common.exception;

import com.jordan.club.common.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException() {

        ErrorResponse response = ErrorResponse.builder()
                .statusCode(NOT_FOUND.value())
                .message("Unable to find the requested resource")
                .build();

        return ResponseEntity.status(NOT_FOUND).body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(UNPROCESSABLE_ENTITY.value())
                .message(ex.getMessage())
                .build();

        return  ResponseEntity.status(UNPROCESSABLE_ENTITY).body(response);
    }

}
