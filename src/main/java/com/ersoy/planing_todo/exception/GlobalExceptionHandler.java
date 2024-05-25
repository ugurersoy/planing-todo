package com.ersoy.planing_todo.exception;


import com.ersoy.planing_todo.domain.dto.response.ErrorResponse;
import com.ersoy.planing_todo.exception.types.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("General error occurred : {}", ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(ex.getMessage())
                .details("General error occurred")
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException ex) {
        log.error("Exception occurred DataNotFoundException : {}", ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(ex.getMessage())
                .details("Exception occurred DataNotFoundException")
                .build(),
                HttpStatus.NOT_FOUND);
    }

}
