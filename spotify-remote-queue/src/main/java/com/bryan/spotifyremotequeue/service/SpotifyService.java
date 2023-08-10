package com.bryan.spotifyremotequeue.service;

import com.bryan.spotifyremotequeue.controller.requests.AuthenticateRequest;
import com.bryan.spotifyremotequeue.controller.requests.SearchRequest;
import com.bryan.spotifyremotequeue.exception.AuthenticateException;
import com.bryan.spotifyremotequeue.service.response.AuthenticateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SpotifyService {

    @Value("${spotify.authorizationHeader}")
    private String authorizationHeader;

    @Value("${spotify.redirectUri}")
    private String redirectUri;

    private String accessToken;

    public AuthenticateResponse authenticate(AuthenticateRequest request) throws AuthenticateException {
        String uri = "https://accounts.spotify.com/api/token";

        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
        bodyValues.add("code", request.getCode());
        bodyValues.add("redirect_uri", redirectUri);
        bodyValues.add("grant_type", "authorization_code");

        WebClient.Builder builder = WebClient.builder();

        try {
            AuthenticateResponse response =
                    builder.build()
                            .post()
                            .uri(uri)
                            .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(authorizationHeader.getBytes()))
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .body(BodyInserters.fromFormData(bodyValues))
                            .retrieve()
                            .bodyToMono(AuthenticateResponse.class)
                            .block();

            System.out.println(response.toString());
            accessToken = response.getAccess_token();

            return response;
        } catch (WebClientResponseException exception) {
            throw new AuthenticateException(exception);
        }
    }

    public String search(SearchRequest request) {
        String uri = generateSearchUri(request);

        WebClient.Builder builder = WebClient.builder();

        try {
            String response =
                    builder.build()
                            .get()
                            .uri(uri)
                            .header("Authorization", "Bearer " + "ACCESSTOKEN")
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();

            System.out.println(response);
        } catch (WebClientResponseException exception) {
            System.out.println(exception.getResponseBodyAsString());
        }
        return "Success";
    }

    private String generateSearchUri(SearchRequest request) {
        StringBuilder stringBuilder = new StringBuilder();

        String uri = stringBuilder
                .append("https://api.spotify.com/v1/search")
                .append("?q=")
                .append(request.getQuery().replace(" ", "%2520"))
                .append("&type=")
                .append(request.getType().stream().map(type -> type).collect(Collectors.joining("%2C")))
                .toString();

        return uri;
    }
}
