package com.bryan.spotifyremotequeue.service.spotify.response.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties
@Data
public
class ExternalIds {
    private String isrc;

    private String ean;

    private String upc;
}
