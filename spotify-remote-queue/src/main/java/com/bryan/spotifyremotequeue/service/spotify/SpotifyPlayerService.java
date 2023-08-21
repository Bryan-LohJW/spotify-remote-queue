package com.bryan.spotifyremotequeue.service.spotify;

import com.bryan.spotifyremotequeue.enums.SpotifyConstants;
import com.bryan.spotifyremotequeue.exception.SpotifyApiException;
import com.bryan.spotifyremotequeue.repository.SpotifyRoomRepository;
import com.bryan.spotifyremotequeue.service.authentication.AuthenticationService;
import com.bryan.spotifyremotequeue.service.spotify.response.PlaybackStateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.function.IntPredicate;

@Service
public class SpotifyPlayerService {

    @Autowired
    private AuthenticationService authenticationService;

    public String addToQueue(String itemUri) throws SpotifyApiException {
        String addToQueueUri = SpotifyConstants.BASE_API + SpotifyConstants.ME + SpotifyConstants.PLAYER + SpotifyConstants.QUEUE;
        String uriParams = "?uri=" + itemUri.trim();
        String accessToken = authenticationService.getAccessToken();
        WebClient.Builder builder = WebClient.builder();
        String response = null;
        try {
            response =
                    builder.build()
                            .post()
                            .uri(addToQueueUri + uriParams)
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
        String nextUri = SpotifyConstants.BASE_API + SpotifyConstants.ME + SpotifyConstants.PLAYER + SpotifyConstants.NEXT;
        String accessToken = authenticationService.getAccessToken();
        WebClient.Builder builder = WebClient.builder();
        String response = null;
        try {
            response =
                    builder.build()
                            .post()
                            .uri(nextUri)
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
        String pauseUri = SpotifyConstants.BASE_API + SpotifyConstants.ME + SpotifyConstants.PLAYER + SpotifyConstants.PAUSE;
        String accessToken = authenticationService.getAccessToken();
        WebClient.Builder builder = WebClient.builder();
        String response = null;
        try {
            response =
                    builder.build()
                            .put()
                            .uri(pauseUri)
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
        String playUri = SpotifyConstants.BASE_API + SpotifyConstants.ME + SpotifyConstants.PLAYER + SpotifyConstants.PLAY;
        String accessToken = authenticationService.getAccessToken();
        WebClient.Builder builder = WebClient.builder();
        String response = null;
        try {
            response =
                    builder.build()
                            .put()
                            .uri(playUri)
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

    public boolean isActive() {
        String playbackStateUri = SpotifyConstants.BASE_API + SpotifyConstants.ME + SpotifyConstants.PLAYER;
        String accessToken = authenticationService.getAccessToken();
        WebClient.Builder builder = WebClient.builder();
        PlaybackStateResponse response = null;

        try {
            response =
                    builder.build()
                            .get()
                            .uri(playbackStateUri)
                            .header("Authorization", "Bearer " + accessToken)
                            .retrieve()
                            .bodyToMono(PlaybackStateResponse.class)
                            .block();
        } catch (WebClientResponseException exception) {
            throw new SpotifyApiException(exception.getStatusCode().value(), "Exception while retrieving playback state");
        }

        if (response != null && response.getDevice() != null) {
            return response.getDevice().is_active();
        }
        return false;
    }
}
