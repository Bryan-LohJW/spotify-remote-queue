package com.bryan.spotifyremotequeue.service.spotify.response;

import com.bryan.spotifyremotequeue.service.spotify.response.common.ExternalUrls;
import com.bryan.spotifyremotequeue.service.spotify.response.common.Followers;
import com.bryan.spotifyremotequeue.service.spotify.response.common.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonIgnoreProperties
@Data
public class CurrentUserProfileResponse {
    private String country;
    @JsonProperty("display_name")
    private String display_name;
    private String email;
    private ExplicitContent explicit_content;
    private ExternalUrls external_urls;
    private Followers followers;
    private String href;
    private String id;
    private Image[] images;
    private String product;
    private String type;
    private String uri;
}

@JsonIgnoreProperties
@Data
class ExplicitContent {
    @JsonProperty("filter_enabled")
    private boolean filter_enabled;
    @JsonProperty("filter_locked")
    private boolean filter_locked;
}
