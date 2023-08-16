package com.bryan.spotifyremotequeue.service;

import com.bryan.spotifyremotequeue.controller.request.AuthenticateRequest;
import com.bryan.spotifyremotequeue.controller.request.RegisterRequest;
import com.bryan.spotifyremotequeue.controller.request.SearchRequest;
import com.bryan.spotifyremotequeue.exception.AuthenticateException;
import com.bryan.spotifyremotequeue.model.SpotifyRoom;
import com.bryan.spotifyremotequeue.model.User;
import com.bryan.spotifyremotequeue.repository.SpotifyRoomRepository;
import com.bryan.spotifyremotequeue.repository.UserRepository;
import com.bryan.spotifyremotequeue.service.response.AuthenticateResponse;
import com.bryan.spotifyremotequeue.service.response.CurrentUserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SpotifyService {
    // this service is meant to create rooms, users, handle spotify api calls

    @Autowired
    private SpotifyRoomRepository spotifyRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${spotify.authorizationHeader}")
    private String authorizationHeader;

    @Value("${spotify.redirectUri}")
    private String redirectUri;

    private String accessToken;

    public User registerRoom(AuthenticateRequest request) throws AuthenticateException {
        String uri = "https://accounts.spotify.com/api/token";

        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
        bodyValues.add("code", request.getCode());
        bodyValues.add("redirect_uri", redirectUri);
        bodyValues.add("grant_type", "authorization_code");

        WebClient.Builder builder = WebClient.builder();
        AuthenticateResponse authenticateResponse = null;
        try {
            authenticateResponse = builder.build()
                    .post()
                    .uri(uri)
                    .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(authorizationHeader.getBytes()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body(BodyInserters.fromFormData(bodyValues))
                    .retrieve()
                    .bodyToMono(AuthenticateResponse.class)
                    .block();
        } catch (WebClientResponseException exception) {
            System.out.println(exception.getResponseBodyAsString());
            throw new AuthenticateException(exception);
        }

        CurrentUserProfileResponse currentUserProfileResponse = null;
        try {
            currentUserProfileResponse = builder.build()
                    .get()
                    .uri("https://api.spotify.com/v1/me")
                    .header("Authorization", "Bearer " + authenticateResponse.getAccess_token())
                    .retrieve()
                    .bodyToMono(CurrentUserProfileResponse.class)
                    .block();
        } catch (WebClientResponseException exception) {
            throw new AuthenticateException(exception);
        }
        SpotifyRoom spotifyRoom = spotifyRoomRepository.save(new SpotifyRoom(authenticateResponse, currentUserProfileResponse.getId()));
        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_OWNER"), new SimpleGrantedAuthority("ROLE_USER"));
        userRepository.save(new User(currentUserProfileResponse.getDisplay_name(), authorities, spotifyRoom));
        return userRepository.save(new User(currentUserProfileResponse.getDisplay_name(), authorities, spotifyRoom));
    }

    public void register(RegisterRequest request) {

    }

    public String search(SearchRequest request) {
        String uri = generateSearchUri(request);

        WebClient.Builder builder = WebClient.builder();

        try {
            String response =
                    builder.build()
                            .get()
                            .uri(uri)
                            .header("Authorization", "Bearer " + accessToken)
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
