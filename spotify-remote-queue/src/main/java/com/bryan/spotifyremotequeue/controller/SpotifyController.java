package com.bryan.spotifyremotequeue.controller;

import com.bryan.spotifyremotequeue.controller.request.AddToQueueRequest;
import com.bryan.spotifyremotequeue.controller.request.RegisterRoomRequest;
import com.bryan.spotifyremotequeue.controller.request.RegisterUserRequest;
import com.bryan.spotifyremotequeue.controller.response.RegisterRoomResponse;
import com.bryan.spotifyremotequeue.controller.response.RegisterUserResponse;
import com.bryan.spotifyremotequeue.controller.response.SuccessResponse;
import com.bryan.spotifyremotequeue.model.SpotifyRoom;
import com.bryan.spotifyremotequeue.model.User;
import com.bryan.spotifyremotequeue.service.authentication.AuthenticationService;
import com.bryan.spotifyremotequeue.service.spotify.SpotifyService;
import com.bryan.spotifyremotequeue.service.spotify.response.SearchResponse;
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
    public ResponseEntity<RegisterRoomResponse> registerRoom(@RequestBody RegisterRoomRequest request) {
        User user = spotifyService.registerRoom(request);
        String token = authenticationService.generateToken(user);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "Authorization");
        headers.add("Authorization", "Bearer " + token);
        return new ResponseEntity(new RegisterRoomResponse(user.getRoom().getRoomId(), user.getRoom().getPin(), user.getRoom().getExpiry()), headers, HttpStatus.OK);
    }

    @PostMapping("registerUser")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserRequest request) {
        User user = spotifyService.registerUser(request);
        String token = authenticationService.generateToken(user);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "Authorization");
        headers.add("Authorization", "Bearer " + token);
        return new ResponseEntity<>(new RegisterUserResponse(user.getRoom().getExpiry()), headers, HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<SearchResponse> search(@RequestParam("query") String query) {
        return ResponseEntity.ok(spotifyService.search(query));
    }

    @PostMapping("addToQueue")
    public ResponseEntity<SuccessResponse> addToQueue(@RequestBody AddToQueueRequest request) {
        return ResponseEntity.ok(new SuccessResponse(spotifyService.addToQueue(request.getItemUri())));
    }
}
