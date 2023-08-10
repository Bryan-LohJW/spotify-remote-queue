package com.bryan.spotifyremotequeue.controller;

import com.bryan.spotifyremotequeue.controller.requests.AuthenticateRequest;
import com.bryan.spotifyremotequeue.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SpotifyController {

    @Autowired
    private SpotifyService spotifyService;

    @PostMapping("authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticateRequest request) {
        return ResponseEntity.ok(spotifyService.authenticate(request));
    }
}
