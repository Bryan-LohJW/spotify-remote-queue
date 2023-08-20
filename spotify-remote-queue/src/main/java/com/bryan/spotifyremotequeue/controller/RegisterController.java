package com.bryan.spotifyremotequeue.controller;

import com.bryan.spotifyremotequeue.controller.request.RegisterRoomRequest;
import com.bryan.spotifyremotequeue.controller.request.RegisterUserRequest;
import com.bryan.spotifyremotequeue.controller.response.RegisterRoomResponse;
import com.bryan.spotifyremotequeue.controller.response.RegisterUserResponse;
import com.bryan.spotifyremotequeue.model.User;
import com.bryan.spotifyremotequeue.service.authentication.AuthenticationService;
import com.bryan.spotifyremotequeue.service.spotify.SpotifyRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/register")
public class RegisterController {

    @Autowired
    private SpotifyRegisterService spotifyRegisterService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("room")
    public ResponseEntity<RegisterRoomResponse> registerRoom(@RequestBody RegisterRoomRequest request) {
        User user = spotifyRegisterService.registerRoom(request);
        String token = authenticationService.generateToken(user);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "Authorization");
        headers.add("Authorization", "Bearer " + token);
        return new ResponseEntity(new RegisterRoomResponse(user.getRoom().getRoomId(), user.getRoom().getPin(), user.getRoom().getExpiry()), headers, HttpStatus.OK);
    }

    @PostMapping("user")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserRequest request) {
        User user = spotifyRegisterService.registerUser(request);
        String token = authenticationService.generateToken(user);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "Authorization");
        headers.add("Authorization", "Bearer " + token);
        return new ResponseEntity<>(new RegisterUserResponse(user.getRoom().getExpiry()), headers, HttpStatus.OK);
    }
}
