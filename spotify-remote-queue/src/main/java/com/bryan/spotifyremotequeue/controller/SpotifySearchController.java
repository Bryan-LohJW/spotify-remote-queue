package com.bryan.spotifyremotequeue.controller;

import com.bryan.spotifyremotequeue.service.spotify.SpotifySearchService;
import com.bryan.spotifyremotequeue.service.spotify.response.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/spotify/search")
public class SpotifySearchController {

    @Autowired
    private SpotifySearchService spotifySearchService;

    @GetMapping("all")
    public ResponseEntity<SearchResponse> search(@RequestParam("query") String query) {
        return ResponseEntity.ok(spotifySearchService.search(query));
    }
}
