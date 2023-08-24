package com.bryan.spotifyremotequeue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SpotifyApiException.class)
    public ResponseEntity<ErrorDetails> handleAuthenticateException(SpotifyApiException exception) {
        ErrorDetails errorDetails = new ErrorDetails(
                exception.getMessage()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ErrorDetails> handleRegistrationException(RegistrationException exception) {
        ErrorDetails errorDetails = new ErrorDetails(
                exception.getMessage()
        );
        return new ResponseEntity<>(errorDetails, exception.getStatus());
    }

    @ExceptionHandler(SpotifySearchException.class)
    public ResponseEntity<ErrorDetails> handleSpotifySearchException(SpotifySearchException exception) {
        ErrorDetails errorDetails = new ErrorDetails(
                exception.getMessage()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleAllException(Exception exception) {
        System.out.println(exception);
        return new ResponseEntity<>(new ErrorDetails(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
