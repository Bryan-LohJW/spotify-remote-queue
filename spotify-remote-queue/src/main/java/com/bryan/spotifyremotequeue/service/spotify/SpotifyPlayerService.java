package com.bryan.spotifyremotequeue.service.spotify;

import com.bryan.spotifyremotequeue.enums.SpotifyConstants;
import com.bryan.spotifyremotequeue.exception.SpotifyApiException;
import com.bryan.spotifyremotequeue.model.SpotifyRoom;
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

    @Autowired
    private SpotifyRoomRepository spotifyRoomRepository;

    public String addToQueue(String itemUri) throws SpotifyApiException {
        checkIsActive();
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
                throw new SpotifyApiException(exception.getStatusCode().value(), "Invalid track id");
            }
            if (exception.getStatusCode().value() == 404) {
                setInactivePlayer();
                throw new SpotifyApiException(exception.getStatusCode().value(), "No player available");
            }
            throw new SpotifyApiException(exception.getStatusCode().value(), "Exception while adding track to playlist");
        }
        return "Success";
    }

    public String skipToNext() {
        checkIsActive();
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
            if (exception.getStatusCode().value() == 404) {
                setInactivePlayer();
                throw new SpotifyApiException(exception.getStatusCode().value(), "No player available");
            }
            throw new SpotifyApiException(exception.getStatusCode().value(), "Exception while skipping to next track");
        }
        return "Success";
    }

    public String pause() {
        checkIsActive();
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
            if (exception.getStatusCode().value() == 404) {
                setInactivePlayer();
                throw new SpotifyApiException(exception.getStatusCode().value(), "No player available");
            }
            throw new SpotifyApiException(exception.getStatusCode().value(), "Exception while pausing");
        }
        return "Success";
    }

    public String play() {
        checkIsActive();
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
            if (exception.getStatusCode().value() == 404) {
                setInactivePlayer();
                throw new SpotifyApiException(exception.getStatusCode().value(), "No player available");
            }
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
        if (response != null &&
                response.getDevice() != null &&
                response.getDevice().is_active()) {
            setActivePlayer();
            return true;
        }
        setInactivePlayer();
        return false;
    }

    private void checkIsActive() {
        String roomId = authenticationService.getRoomId();
        SpotifyRoom spotifyRoom = spotifyRoomRepository.findById(roomId).orElseThrow(() -> {
            throw new SpotifyApiException(404, "Room not found");
        });
        if (!spotifyRoom.isActive()) {
            throw new SpotifyApiException(404, "No player available");
        }
    }

    private void setInactivePlayer() {
        String roomId = authenticationService.getRoomId();
        SpotifyRoom spotifyRoom = spotifyRoomRepository.findById(roomId).orElseThrow(() -> {
            throw new SpotifyApiException(404, "Room not found");
        });
        spotifyRoom.setActive(false);
        spotifyRoomRepository.save(spotifyRoom);
    }

    private void setActivePlayer() {
        String roomId = authenticationService.getRoomId();
        SpotifyRoom spotifyRoom = spotifyRoomRepository.findById(roomId).orElseThrow(() -> {
            throw new SpotifyApiException(404, "Room not found");
        });
        spotifyRoom.setActive(true);
        spotifyRoomRepository.save(spotifyRoom);
    }
}
