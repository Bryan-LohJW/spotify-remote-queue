package com.bryan.spotifyremotequeue.service.spotify;

import com.bryan.spotifyremotequeue.enums.PLAYER_STATE;
import com.bryan.spotifyremotequeue.enums.SpotifyConstants;
import com.bryan.spotifyremotequeue.exception.SpotifyApiException;
import com.bryan.spotifyremotequeue.model.SpotifyRoom;
import com.bryan.spotifyremotequeue.repository.SpotifyRoomRepository;
import com.bryan.spotifyremotequeue.service.authentication.AuthenticationService;
import com.bryan.spotifyremotequeue.service.spotify.response.PlaybackStateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@Service
public class SpotifyPlayerService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SpotifyRoomRepository spotifyRoomRepository;

    public String addToQueue(String itemUri) throws SpotifyApiException {
        isActive();
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
        } catch (WebClientResponseException exception) {
            if (exception.getStatusCode().value() == 400) {
                throw new SpotifyApiException("Invalid track id", HttpStatus.BAD_REQUEST);
            }
            throw new SpotifyApiException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return "Success";
    }

    public String skipToNext() {
        isActive();
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
        } catch (WebClientResponseException exception) {
            throw new SpotifyApiException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return "Success";
    }

    public String pause() {
        isActive();
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
        } catch (WebClientResponseException exception) {
            if (exception.getStatusCode().value() == 403) {
                throw new SpotifyApiException("Already paused", HttpStatus.FORBIDDEN);
            }
            throw new SpotifyApiException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return "Success";
    }

    public String play() {
        isActive();
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
        } catch (WebClientResponseException exception) {
            if (exception.getStatusCode().value() == 403) {
                throw new SpotifyApiException("Already playing", HttpStatus.FORBIDDEN);
            }
            throw new SpotifyApiException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return "Success";
    }

    public void isActive() {
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
            throw new SpotifyApiException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (response == null) {
            throw new SpotifyApiException(String.valueOf(PLAYER_STATE.NO_DEVICE), HttpStatus.NOT_FOUND);

        }
        if (!response.getDevice().is_active()) {
            throw new SpotifyApiException(String.valueOf(PLAYER_STATE.NOT_ACTIVE), HttpStatus.NOT_FOUND);
        }
    }
}
