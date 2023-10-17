package com.bryan.spotifyremotequeue.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Setter
@Getter
public class SpotifySearchException extends RuntimeException {

    private HttpStatus status;

    public SpotifySearchException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
