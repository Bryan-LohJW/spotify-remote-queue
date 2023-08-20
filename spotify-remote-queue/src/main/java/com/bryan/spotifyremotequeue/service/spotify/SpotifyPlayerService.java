package com.bryan.spotifyremotequeue.service.spotify;

import com.bryan.spotifyremotequeue.exception.SpotifyApiException;
import com.bryan.spotifyremotequeue.repository.SpotifyRoomRepository;
import com.bryan.spotifyremotequeue.service.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class SpotifyPlayerService {

    @Autowired
    private AuthenticationService authenticationService;

    public String addToQueue(String itemUri) throws SpotifyApiException {
        String uri = "https://api.spotify.com/v1/me/player/queue?uri=" + itemUri.trim();
        String accessToken = authenticationService.getAccessToken();
        WebClient.Builder builder = WebClient.builder();
        String response = null;
        try {
            response =
                    builder.build()
                            .post()
                            .uri(uri)
                            .header("Authorization", "Bearer " + accessToken)
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();

            System.out.println(response);
        } catch (WebClientResponseException exception) {
            throw new SpotifyApiException(exception.getStatusCode().value(), "Exception while adding track to playlist");
        }
        return "Success";
    }

    public String skipToNext() {
        String uri = "https://api.spotify.com/v1/me/player/next";
        String accessToken = authenticationService.getAccessToken();
        WebClient.Builder builder = WebClient.builder();
        String response = null;
        try {
            response =
                    builder.build()
                            .post()
                            .uri(uri)
                            .header("Authorization", "Bearer " + accessToken)
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();

            System.out.println(response);
        } catch (WebClientResponseException exception) {
            throw new SpotifyApiException(exception.getStatusCode().value(), "Exception while skipping to next track");
        }
        return "Success";
    }

    public String pause() {
        String uri = "https://api.spotify.com/v1/me/player/pause";
        String accessToken = authenticationService.getAccessToken();
        WebClient.Builder builder = WebClient.builder();
        String response = null;
        try {
            response =
                    builder.build()
                            .put()
                            .uri(uri)
                            .header("Authorization", "Bearer " + accessToken)
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();

            System.out.println(response);
        } catch (WebClientResponseException exception) {
            throw new SpotifyApiException(exception.getStatusCode().value(), "Exception while pausing");
        }
        return "Success";
    }

    public String play() {
        String uri = "https://api.spotify.com/v1/me/player/play";
        String accessToken = authenticationService.getAccessToken();
        WebClient.Builder builder = WebClient.builder();
        String response = null;
        try {
            response =
                    builder.build()
                            .put()
                            .uri(uri)
                            .header("Authorization", "Bearer " + accessToken)
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();

            System.out.println(response);
        } catch (WebClientResponseException exception) {
            throw new SpotifyApiException(exception.getStatusCode().value(), "Exception while playing");
        }
        return "Success";
    }
}
