package com.bryan.spotifyremotequeue.service.spotify.response.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties
@Data
public class Image {
    private String url;

    private int height;

    private int width;
}
