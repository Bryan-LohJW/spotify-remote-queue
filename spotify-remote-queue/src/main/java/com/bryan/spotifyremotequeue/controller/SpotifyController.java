package com.bryan.spotifyremotequeue.controller;

import com.bryan.spotifyremotequeue.controller.requests.AuthenticateRequest;
import com.bryan.spotifyremotequeue.service.SpotifyService;
import com.bryan.spotifyremotequeue.service.response.AuthenticateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1")
public class SpotifyController {

    @Autowired
    private SpotifyService spotifyService;

    @PostMapping("authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticateRequest request) {
        spotifyService.authenticate(request);
        return ResponseEntity.ok("Successfully authenticated");
    }
}
