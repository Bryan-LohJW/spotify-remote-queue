package com.bryan.spotifyremotequeue.service.spotify.response.common;

import lombok.Data;

@Data
public class TrackObject {
    private TracksTrackAlbum album;

    private TracksTrackArtist[] artists;

    private String[] available_markets;

    private int disc_number;

    private int duration_ms;

    private boolean explicit;

    private ExternalIds external_ids;

    private ExternalUrls external_urls;

    private String href;

    private String id;

    private boolean is_playable;

    private TracksTrackLinkedFrom linked_from;

    private Restrictions restrictions;

    private String name;

    private int popularity;

    private String preview_url;

    private int track_number;

    private String type;

    private String uri;

    private boolean is_local;
}

@Data
class TracksTrackAlbum {
    private String album_type;

    private int total_tracks;

    private String[] available_markets;

    private ExternalUrls external_urls;

    private String href;

    private String id;

    private Image[] images;

    private String name;

    private String release_date;

    private String release_date_precision;

    private Restrictions restrictions;

    private String type;

    private String uri;

    private Copyright[] copyrights;

    private ExternalIds external_ids;

    private String[] genres;

    private String label;

    private int popularity;

    private String album_group;

    private TracksTrackAlbumArtist[] artists;
}

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

@Data
class TracksTrackLinkedFrom {
}

@Data
class TracksTrackAlbumArtist {
    private ExternalUrls external_urls;

    private String href;

    private String id;

    private String name;

    private String type;

    private String uri;
}