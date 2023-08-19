package com.bryan.spotifyremotequeue.exception;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public class SpotifySearchException extends RuntimeException {
    public SpotifySearchException(String message) {
        super(message);
    }
}
