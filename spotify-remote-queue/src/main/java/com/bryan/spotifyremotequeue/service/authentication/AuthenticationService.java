package com.bryan.spotifyremotequeue.service.authentication;

import com.bryan.spotifyremotequeue.config.security.Principal;
import com.bryan.spotifyremotequeue.exception.RegistrationException;
import com.bryan.spotifyremotequeue.model.SpotifyRoom;
import com.bryan.spotifyremotequeue.model.User;
import com.bryan.spotifyremotequeue.repository.SpotifyRoomRepository;
import com.bryan.spotifyremotequeue.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthenticationService {

    @Autowired
    private SpotifyRoomRepository spotifyRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    public String getAccessToken() {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SpotifyRoom spotifyRoom = spotifyRoomRepository.findById(principal.getRoomId()).orElseThrow(() -> {
            throw new RuntimeException();
        });
        return spotifyRoom.getAccessToken();
    }

    public SpotifyRoom authenticateRoomJoining(String roomId, String pin, String userId) {
        SpotifyRoom spotifyRoom = spotifyRoomRepository.findById(roomId).orElseThrow(() -> {
            throw new RegistrationException("Invalid room id", HttpStatus.BAD_REQUEST);
        });
        if (!spotifyRoom.getPin().equals(pin)) {
            throw new RegistrationException("Invalid pin", HttpStatus.BAD_REQUEST);
        }
        Optional<User> optionalUser = userRepository.findByUserIdAndRoomId(userId, roomId);
        if (optionalUser.isPresent()) {
            throw new RegistrationException("Username taken", HttpStatus.BAD_REQUEST);
        }
        return spotifyRoom;
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
