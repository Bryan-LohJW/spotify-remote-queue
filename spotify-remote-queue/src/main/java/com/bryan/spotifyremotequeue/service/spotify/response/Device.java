package com.bryan.spotifyremotequeue.service.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties
@Data
public class Device {
    private String id;
    @JsonProperty("is_active")
    private boolean is_active;
    @JsonProperty("is_private_session")
    private boolean is_private_session;
    @JsonProperty("is_restricted")
    private boolean is_restricted;

    private String name;

    private String type;
//    @JsonProperty("volume_percent")
//    private long volume_percent;
}
