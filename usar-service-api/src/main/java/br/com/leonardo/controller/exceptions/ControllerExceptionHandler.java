package br.com.leonardo.controller.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import models.exceptions.ResourceNotFoundException;
import models.exceptions.StandarError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<StandarError> handleNotFoundException(final ResourceNotFoundException ex, HttpServletRequest request){
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
}
