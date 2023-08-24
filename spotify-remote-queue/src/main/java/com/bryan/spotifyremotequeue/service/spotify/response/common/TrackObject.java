package com.bryan.spotifyremotequeue.service.spotify.response.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties
@Data
public class TrackObject {
    private TracksTrackAlbum album;

    private TracksTrackArtist[] artists;

    private String[] available_markets;

    @JsonProperty("disc_number")
    private int disc_number;
    @JsonProperty("duration_ms")
    private int duration_ms;

    private boolean explicit;

    private ExternalIds external_ids;

    private ExternalUrls external_urls;

    private String href;

    private String id;
    @JsonProperty("is_playable")
    private boolean is_playable;

    private TracksTrackLinkedFrom linked_from;

    private Restrictions restrictions;

    private String name;

    private int popularity;
    @JsonProperty("preview_url")
    private String preview_url;
    @JsonProperty("track_number")
    private int track_number;

    private String type;

    private String uri;
    @JsonProperty("is_local")
    private boolean is_local;
}

@JsonIgnoreProperties
@Data
class TracksTrackAlbum {
    private String album_type;
    @JsonProperty("total_tracks")
    private int total_tracks;

    private String[] available_markets;

    private ExternalUrls external_urls;

    private String href;

    private String id;

    private Image[] images;

    private String name;
    @JsonProperty("release_date")
    private String release_date;
    @JsonProperty("release_date_precision")
    private String release_date_precision;

    private Restrictions restrictions;

    private String type;

    private String uri;

    private Copyright[] copyrights;

    private ExternalIds external_ids;

    private String[] genres;

    private String label;

    private int popularity;
    @JsonProperty("album_group")
    private String album_group;

    private TracksTrackAlbumArtist[] artists;
}

@JsonIgnoreProperties
@Data
class TracksTrackArtist {
    private ExternalUrls external_urls;

    private Followers followers;

    private String[] genres;

    private String href;

    private String id;

    private Image[] images;

    private String name;

    private int popularity;

    private String type;

    private String uri;
}

@JsonIgnoreProperties
@Data
class TracksTrackLinkedFrom {
}

@JsonIgnoreProperties
@Data
class TracksTrackAlbumArtist {
    private ExternalUrls external_urls;

    private String href;

    private String id;

    private String name;

    private String type;

    private String uri;
}