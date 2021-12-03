package com.example.estore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler{

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)+" "+request.getContextPath());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DependenciesFoundException.class})
    public ResponseEntity<ErrorMessage> dependenciesFoundException(DependenciesFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.PRECONDITION_FAILED.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)+" "+request.getContextPath());

        return new ResponseEntity<ErrorMessage>(message,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = {DuplicateResourceException.class})
    public ResponseEntity<ErrorMessage> duplicateResourceException(DuplicateResourceException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)+" "+request.getContextPath());

        return new ResponseEntity<ErrorMessage>(message,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = {UnsupportedCategoryException.class})
    public ResponseEntity<ErrorMessage> unsupportedCategoryException(UnsupportedCategoryException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)+" "+request.getContextPath());

        return new ResponseEntity<ErrorMessage>(message,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
