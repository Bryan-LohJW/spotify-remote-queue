package com.bryan.spotifyremotequeue.model;

import com.bryan.spotifyremotequeue.service.authentication.response.AuthenticateResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyRoom {
    @Id
    @GenericGenerator(name = "room_id", strategy = "com.bryan.spotifyremotequeue.repository.SpotifyRoomIdGenerator")
    @GeneratedValue(generator = "room_id")
    private String roomId;

    private String pin;

    private String accessToken;

    private String owner;

    private LocalDateTime expiry;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    public SpotifyRoom(AuthenticateResponse response, String owner) {
        int targetStringLength = 6;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < targetStringLength; i++) {
            buffer.append(random.nextInt(10));
        }
        this.pin = buffer.toString();
        this.accessToken = response.getAccess_token();
        this.owner = owner;
        this.expiry = LocalDateTime.now().plus(Duration.ofSeconds(response.getExpires_in()));
        this.users = new ArrayList<>();
    }

}
