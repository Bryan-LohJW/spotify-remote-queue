package com.bryan.spotifyremotequeue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticateException.class)
    public ResponseEntity<ErrorDetails> handleAuthenticateException(AuthenticateException exception) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                "AUTHENTICATION_EXCEPTION"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleAllException(Exception exception) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(),
                exception.getMessage(),
                "INTERNAL_SERVER_ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
