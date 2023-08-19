package com.bryan.spotifyremotequeue.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserProfileResponse {
    private String country;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ExplicitContent {
    private boolean filter_enabled;
    private boolean filter_locked;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ExternalUrls {
    private String spotify;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Followers {
    private String href;
    private long total;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Image {
    private String url;
    private Long height;
    private Long width;
}