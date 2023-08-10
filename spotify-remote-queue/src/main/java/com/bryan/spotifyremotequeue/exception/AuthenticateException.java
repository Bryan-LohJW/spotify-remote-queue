package com.bryan.spotifyremotequeue.exception;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public class AuthenticateException extends WebClientResponseException {

    public AuthenticateException(WebClientResponseException exception) {
        super(exception.getMessage(),
                exception.getStatusCode(),
                exception.getStatusText(),
                exception.getHeaders(),
                exception.getResponseBodyAsByteArray(),
                null,
                exception.getRequest());
    }
}
