package br.com.leonardo.authserviceapi.controllers.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import models.exceptions.ResourceNotFoundException;
import models.exceptions.StandarError;
import models.exceptions.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.security.authentication.BadCredentialsException;
=======
>>>>>>> origin/develop
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ControllerExceptionHandler {

<<<<<<< HEAD
    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<StandarError> handleBadCredentialsException(final BadCredentialsException ex, final HttpServletRequest request) {
        return ResponseEntity.status(UNAUTHORIZED)
                .body(
                        StandarError.builder()
                                .timestamp(now())
                                .status(UNAUTHORIZED.value())
                                .error(UNAUTHORIZED.getReasonPhrase())
=======
    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<StandarError> handleUsernameNotFoundException(final UsernameNotFoundException ex, final HttpServletRequest request) {
        return ResponseEntity.status(NOT_FOUND)
                .body(
                        StandarError.builder()
                                .timestamp(now())
                                .status(NOT_FOUND.value())
                                .error(NOT_FOUND.getReasonPhrase())
>>>>>>> origin/develop
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

