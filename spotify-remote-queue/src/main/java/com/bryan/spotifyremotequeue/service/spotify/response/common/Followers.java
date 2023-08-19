package com.bryan.spotifyremotequeue.service.spotify.response.common;

import lombok.Data;

@Data
public class Followers {
    private String href;

    private int total;
}
