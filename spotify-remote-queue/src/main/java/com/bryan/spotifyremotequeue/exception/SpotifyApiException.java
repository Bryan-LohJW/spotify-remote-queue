package com.bryan.spotifyremotequeue.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Getter
@Setter
public class SpotifyApiException extends WebClientResponseException {

    private HttpStatus status;

    public SpotifyApiException(int statusCode, String message) {
        super(statusCode, message, null, null, null);
    }

    public SpotifyApiException(String message, HttpStatus status) {
        super(message, status.value(), status.getReasonPhrase(), null, null, null);
        this.status = status;
    }
}
