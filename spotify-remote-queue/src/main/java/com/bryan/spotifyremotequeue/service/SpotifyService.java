package com.bryan.spotifyremotequeue.service;

import com.bryan.spotifyremotequeue.controller.requests.AuthenticateRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Base64;

@Service
public class SpotifyService {

    @Value("${spotify.authorizationHeader}")
    private String authorizationHeader;

    @Value("${spotify.redirectUri}")
    private String redirectUri;

    public String authenticate(AuthenticateRequest request) {
        String uri = "https://accounts.spotify.com/api/token";

        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
        bodyValues.add("code", request.getCode());
        bodyValues.add("redirect_uri", redirectUri);
        bodyValues.add("grant_type", "authorization_code");

        WebClient.Builder builder = WebClient.builder();

        try {
            String response =
                    builder.build()
                            .post()
                            .uri(uri)
                            .header("Authorization", Base64.getEncoder().encodeToString(authorizationHeader.getBytes()))
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .body(BodyInserters.fromFormData(bodyValues))
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();

            System.out.println(response);
        } catch (WebClientResponseException exception) {
            System.out.println(exception.getResponseBodyAsString());
        }

        return "Success";
    }
}
