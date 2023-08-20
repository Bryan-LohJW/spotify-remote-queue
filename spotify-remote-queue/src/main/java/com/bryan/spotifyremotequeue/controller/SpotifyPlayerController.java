package com.bryan.spotifyremotequeue.controller;

import com.bryan.spotifyremotequeue.controller.request.AddToQueueRequest;
import com.bryan.spotifyremotequeue.controller.response.SuccessResponse;
import com.bryan.spotifyremotequeue.service.spotify.SpotifyPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/spotify/player")
public class SpotifyPlayerController {

    @Autowired
    private SpotifyPlayerService spotifyPlayerService;

    @PostMapping("add")
    public ResponseEntity<SuccessResponse> addToQueue(@RequestBody AddToQueueRequest request) {
        return ResponseEntity.ok(new SuccessResponse(spotifyPlayerService.addToQueue(request.getItemUri())));
    }

    @PostMapping("next")
    public ResponseEntity<SuccessResponse> skipToNext() {
        return ResponseEntity.ok(new SuccessResponse(spotifyPlayerService.skipToNext()));
    }

    @PutMapping("play")
    public ResponseEntity<SuccessResponse> play() {
        return ResponseEntity.ok(new SuccessResponse(spotifyPlayerService.play()));
    }

    @PutMapping("pause")
    public ResponseEntity<SuccessResponse> pause() {
        return ResponseEntity.ok(new SuccessResponse(spotifyPlayerService.pause()));
    }
}
