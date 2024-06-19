package com.marcosrod.serviceorder.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleAttributeValidation(MethodArgumentNotValidException ex) {
        var error = ex.getBindingResult().getFieldError().getDefaultMessage();
        var fieldName = ex.getBindingResult().getFieldError().getField();
        var errorMessage = "Error parsing the field: " + fieldName + " -> " + error;

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}