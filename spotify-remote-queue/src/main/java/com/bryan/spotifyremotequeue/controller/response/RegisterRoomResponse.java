package com.bryan.spotifyremotequeue.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRoomResponse {
    private String roomId;

    private String pin;

    private LocalDateTime expiry;
}
