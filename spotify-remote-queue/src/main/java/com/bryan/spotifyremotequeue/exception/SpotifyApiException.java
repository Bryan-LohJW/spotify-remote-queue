package com.bryan.spotifyremotequeue.exception;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public class SpotifyApiException extends WebClientResponseException {

    public SpotifyApiException(int statusCode, String message) {
        super(statusCode, message, null, null, null);
    }
}
