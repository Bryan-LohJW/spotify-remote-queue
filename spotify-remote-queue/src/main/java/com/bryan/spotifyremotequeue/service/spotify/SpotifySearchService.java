package com.bryan.spotifyremotequeue.service.spotify;

import com.bryan.spotifyremotequeue.exception.SpotifyApiException;
import com.bryan.spotifyremotequeue.repository.SpotifyRoomRepository;
import com.bryan.spotifyremotequeue.service.authentication.AuthenticationService;
import com.bryan.spotifyremotequeue.service.spotify.response.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpotifySearchService {

    @Autowired
    private AuthenticationService authenticationService;

    public SearchResponse search(String query) throws SpotifyApiException {
        String uri = generateSearchUri(query);
        WebClient.Builder builder = WebClient.builder();
        String accessToken = authenticationService.getAccessToken();
        SearchResponse response = null;
        try {
            response =
                    builder.build()
                            .get()
                            .uri(uri)
                            .header("Authorization", "Bearer " + accessToken)
                            .retrieve()
                            .bodyToMono(SearchResponse.class)
                            .block();

        } catch (WebClientResponseException exception) {
            throw new SpotifyApiException(exception.getStatusCode().value(), "Exception while searching");
        }
        return response;
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
