package com.bryan.spotifyremotequeue.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationException extends RuntimeException {

    private HttpStatus status;

    public RegistrationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
