package com.bryan.spotifyremotequeue.service.spotify.response.common;

import lombok.Data;

// common
@Data
public
class ExternalIds {
    private String isrc;

    private String ean;

    private String upc;
}
