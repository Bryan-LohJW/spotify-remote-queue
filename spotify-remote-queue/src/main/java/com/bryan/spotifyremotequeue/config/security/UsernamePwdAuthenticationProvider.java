package com.bryan.spotifyremotequeue.config.security;

import com.bryan.spotifyremotequeue.model.SpotifyRoom;
import com.bryan.spotifyremotequeue.model.User;
import com.bryan.spotifyremotequeue.repository.SpotifyRoomRepository;
import com.bryan.spotifyremotequeue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsernamePwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpotifyRoomRepository spotifyRoomRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Principal principal = (Principal) authentication.getPrincipal();
        String rawPin = authentication.getCredentials().toString();
        Optional<SpotifyRoom> optionalSpotifyRoom = spotifyRoomRepository.findById(principal.getRoomId());
        if (optionalSpotifyRoom.isEmpty()) {
            throw new BadCredentialsException("Invalid room");
        }
        SpotifyRoom spotifyRoom = optionalSpotifyRoom.get();
        if (!passwordEncoder.matches(rawPin, spotifyRoom.getPin())) {
            throw new BadCredentialsException("Invalid pin");
        }
        if (userRepository.existsById(principal.getUserId())) {
            throw new BadCredentialsException("Username exists");
        }
        userRepository.save(new User(principal.getUserId(), spotifyRoom));

        return new UsernamePasswordAuthenticationToken(principal, rawPin);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
