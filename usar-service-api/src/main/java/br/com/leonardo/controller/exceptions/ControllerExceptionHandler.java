package br.com.leonardo.controller.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import models.exceptions.ResourceNotFoundException;
import models.exceptions.StandarError;
import models.exceptions.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<StandarError> handleNotFoundException(final ResourceNotFoundException ex, final HttpServletRequest request) {
        return ResponseEntity.status(NOT_FOUND)
                .body(
                        StandarError.builder()
                                .timestamp(now())
                                .status(NOT_FOUND.value())
                                .error(NOT_FOUND.getReasonPhrase())
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build()
                );
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<StandarError> handleDataIntegrityViolationException(final DataIntegrityViolationException ex, final HttpServletRequest request) {
        return ResponseEntity.status(CONFLICT).body(
                        StandarError.builder()
                                .timestamp(now())
                                .status(CONFLICT.value())
                                .error(CONFLICT.getReasonPhrase())
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationException> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex, final HttpServletRequest request) {

        var error = ValidationException.builder()
                .timestamp(now())
                .status(BAD_REQUEST.value())
                .error("Validation exception")
                .message("Exception in validation")
                .path(request.getRequestURI())
                .errors(new ArrayList<>())
                .build();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(error);
    }
}
