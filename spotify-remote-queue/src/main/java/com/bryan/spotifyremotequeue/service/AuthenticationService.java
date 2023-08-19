package com.bryan.spotifyremotequeue.service;

import com.bryan.spotifyremotequeue.model.SpotifyRoom;
import com.bryan.spotifyremotequeue.model.User;
import com.bryan.spotifyremotequeue.repository.SpotifyRoomRepository;
import com.bryan.spotifyremotequeue.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthenticationService {

    // this service is to generate tokens when user registers

    @Autowired
    private SpotifyRoomRepository spotifyRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    public boolean authenticateRoomJoining(String roomId, String pin) {
        SpotifyRoom spotifyRoom = spotifyRoomRepository.findById(roomId).orElseThrow(() -> {
            throw new BadCredentialsException("Invalid room");
        });
        if (!spotifyRoom.getPin().equals(pin)) {
            throw new BadCredentialsException("Invalid pin");
        }
        return true;
    }

    public String generateToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        String jwt = Jwts.builder()
                .setIssuer("Remote queue for spotify")
                .setSubject("JWT Token")
                .claim("username", user.getUserId())
                .claim("roomId", user.getRoom().getRoomId())
                .claim("authorities", populateAuthorities(user.getAuthorities()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 3600000))
                .signWith(key)
                .compact();
        return jwt;
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}
