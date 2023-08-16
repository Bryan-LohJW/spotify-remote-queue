package com.bryan.spotifyremotequeue.controller;

import com.bryan.spotifyremotequeue.controller.request.RegisterRoomRequest;
import com.bryan.spotifyremotequeue.controller.request.RegisterUserRequest;
import com.bryan.spotifyremotequeue.controller.request.SearchRequest;
import com.bryan.spotifyremotequeue.model.SpotifyRoom;
import com.bryan.spotifyremotequeue.model.User;
import com.bryan.spotifyremotequeue.service.AuthenticationService;
import com.bryan.spotifyremotequeue.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/spotify")
public class SpotifyController {

    @Autowired
    private SpotifyService spotifyService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("registerRoom")
    public ResponseEntity<SpotifyRoom> registerRoom(@RequestBody RegisterRoomRequest request) {
        User user = spotifyService.registerRoom(request);
        String token = authenticationService.generateToken(user);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return new ResponseEntity<>(user.getRoom(), headers, HttpStatus.OK);
    }

    @PostMapping("registerUser")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserRequest request) {
        User user = spotifyService.registerUser(request);
        String token = authenticationService.generateToken(user);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return new ResponseEntity<>("SUCCESS", headers, HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<String> search(@RequestBody SearchRequest request) {
        spotifyService.search(request);
        return ResponseEntity.ok("Successfully searched");
    }

    @GetMapping("test")
    public String test() {
        return "Able to access endpoint";
    }
}
