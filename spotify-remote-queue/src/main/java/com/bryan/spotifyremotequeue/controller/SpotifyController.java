package com.bryan.spotifyremotequeue.controller;

import com.bryan.spotifyremotequeue.controller.request.AuthenticateRequest;
import com.bryan.spotifyremotequeue.controller.request.SearchRequest;
import com.bryan.spotifyremotequeue.model.SpotifyRoom;
import com.bryan.spotifyremotequeue.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1")
public class SpotifyController {

    @Autowired
    private SpotifyService spotifyService;

    @PostMapping("authenticate")
    public ResponseEntity<SpotifyRoom> authenticate(@RequestBody AuthenticateRequest request) {
        SpotifyRoom spotifyRoom = spotifyService.authenticate(request);
        return new ResponseEntity<>(spotifyRoom, HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<String> search(@RequestBody SearchRequest request) {
        spotifyService.search(request);
        return ResponseEntity.ok("Successfully searched");
    }
}
