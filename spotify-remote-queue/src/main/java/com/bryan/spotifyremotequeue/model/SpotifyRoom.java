package com.bryan.spotifyremotequeue.model;

import com.bryan.spotifyremotequeue.repository.SpotifyRoomIdGenerator;
import com.bryan.spotifyremotequeue.service.response.AuthenticateResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
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
    }

}
