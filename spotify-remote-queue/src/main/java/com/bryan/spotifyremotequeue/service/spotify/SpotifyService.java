package com.bryan.spotifyremotequeue.service.spotify;

import com.bryan.spotifyremotequeue.config.security.Principal;
import com.bryan.spotifyremotequeue.controller.request.RegisterRoomRequest;
import com.bryan.spotifyremotequeue.controller.request.RegisterUserRequest;
import com.bryan.spotifyremotequeue.exception.RegistrationException;
import com.bryan.spotifyremotequeue.exception.SpotifyApiException;
import com.bryan.spotifyremotequeue.exception.SpotifySearchException;
import com.bryan.spotifyremotequeue.model.SpotifyRoom;
import com.bryan.spotifyremotequeue.model.User;
import com.bryan.spotifyremotequeue.repository.SpotifyRoomRepository;
import com.bryan.spotifyremotequeue.repository.UserRepository;
import com.bryan.spotifyremotequeue.service.authentication.response.AuthenticateResponse;
import com.bryan.spotifyremotequeue.service.spotify.response.CurrentUserProfileResponse;
import com.bryan.spotifyremotequeue.service.spotify.response.SearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.*;
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

    private ObjectMapper mapper = new ObjectMapper();

    @Transactional
    public User registerRoom(RegisterRoomRequest request) throws SpotifyApiException {
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
            throw new SpotifyApiException(exception.getStatusCode().value(), "Exception while getting Spotify access token");
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
            throw new SpotifyApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Exception while getting current user profile");
        }
        if (spotifyRoomRepository.existsByOwner(currentUserProfileResponse.getId())) {
            //https://thorben-janssen.com/avoid-cascadetype-delete-many-assocations/
            SpotifyRoom room = spotifyRoomRepository.findByOwner(currentUserProfileResponse.getId());
            spotifyRoomRepository.deleteByOwner(currentUserProfileResponse.getId());
        }
        SpotifyRoom spotifyRoom = spotifyRoomRepository.save(new SpotifyRoom(authenticateResponse, currentUserProfileResponse.getId()));
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(Arrays.asList("ROLE_OWNER", "ROLE_USER"));
        userRepository.save(new User(currentUserProfileResponse.getDisplay_name(), authorities, spotifyRoom));
        return userRepository.save(new User(currentUserProfileResponse.getDisplay_name(), authorities, spotifyRoom));
    }

    public User registerUser(RegisterUserRequest request) throws SpotifyApiException {
        SpotifyRoom room = spotifyRoomRepository.findById(request.getRoomId()).orElseGet(() -> {
            throw new RegistrationException("Invalid room id", HttpStatus.BAD_REQUEST);
        });
        if (!room.getPin().equals(request.getPin())) {
            throw new RegistrationException("Invalid pin", HttpStatus.BAD_REQUEST);
        }
        Optional<User> optionalUser = userRepository.findByUserIdAndRoomId(request.getUserId(), request.getRoomId());
        if (optionalUser.isPresent()) {
            throw new RegistrationException("Username taken", HttpStatus.BAD_REQUEST);
        }

        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(Arrays.asList("ROLE_USER"));
        return userRepository.save(new User(request.getUserId(), authorities, room));
    }

    public SearchResponse search(String query) throws SpotifyApiException {
        String uri = generateSearchUri(query);
        WebClient.Builder builder = WebClient.builder();
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SpotifyRoom spotifyRoom = spotifyRoomRepository.findById(principal.getRoomId()).orElseThrow();
        SearchResponse response = null;
        try {
            response =
                    builder.build()
                            .get()
                            .uri(uri)
                            .header("Authorization", "Bearer " + spotifyRoom.getAccessToken())
                            .retrieve()
                            .bodyToMono(SearchResponse.class)
                            .block();

        } catch (WebClientResponseException exception) {
            throw new SpotifyApiException(exception.getStatusCode().value(), "Exception while searching");
        }
        return response;
    }

    public String addToQueue(String itemUri) throws SpotifyApiException {
        String uri = "https://api.spotify.com/v1/me/player/queue?uri=" + itemUri.trim();
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SpotifyRoom spotifyRoom = spotifyRoomRepository.findById(principal.getRoomId()).orElseThrow();
        WebClient.Builder builder = WebClient.builder();
        String response = null;
        try {
            response =
                    builder.build()
                            .post()
                            .uri(uri)
                            .header("Authorization", "Bearer " + spotifyRoom.getAccessToken())
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();

            System.out.println(response);
        } catch (WebClientResponseException exception) {
            throw new SpotifyApiException(exception.getStatusCode().value(), "Exception while adding track to playlist");
        }
        return "Success";
    }

    private String generateSearchUri(String query) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> type = Arrays.asList("album", "artist", "playlist", "track", "show", "audiobook");

        String uri = stringBuilder
                .append("https://api.spotify.com/v1/search")
                .append("?q=")
                .append(query.trim().replace(" ", "+"))
                .append("&type=")
                .append(type.stream().collect(Collectors.joining(",")))
                .toString();

        return uri;
    }
}
