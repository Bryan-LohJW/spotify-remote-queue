package com.bryan.spotifyremotequeue.service.spotify.response.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties
@Data
public
class Copyright {
    private String text;

    private String type;
}
