package com.bryan.spotifyremotequeue.service.spotify.response;

import com.bryan.spotifyremotequeue.service.spotify.response.common.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonIgnoreProperties
@Data
public class SearchResponse {

    private Tracks tracks;

    private Artists artists;

    private Albums albums;

    private Playlists playlists;

    private Shows shows;

    private Episodes episodes;

    private Audiobooks audiobooks;
}

@JsonIgnoreProperties
@Data
class Tracks {
    private String href;

    private int limit;

    private String next;

    private int offset;

    private String previous;

    private int total;

    private TrackObject[] items;
}

@JsonIgnoreProperties
@Data
class Artists {
    private String href;

    private int limit;

    private String next;

    private int offset;

    private String previous;

    private int total;

    private ArtistsArtist[] items;
}

@JsonIgnoreProperties
@Data
class ArtistsArtist {
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
class Albums {

    private String href;

    private int limit;

    private String next;

    private int offset;

    private String previous;

    private int total;

    private AlbumsAlbum[] items;
}

@JsonIgnoreProperties
@Data
class AlbumsAlbum {
    @JsonProperty("album_type")
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

    private AlbumsAlbumArtist[] artists;


}

@JsonIgnoreProperties
@Data
class AlbumsAlbumArtist {
    private ExternalUrls external_urls;

    private String href;

    private String id;

    private String name;

    private String type;

    private String uri;
}

@JsonIgnoreProperties
@Data
class Playlists {
    private String href;

    private int limit;

    private String next;

    private int offset;

    private String previous;

    private int total;

    private PlaylistsPlaylist[] items;
}

@JsonIgnoreProperties
@Data
class PlaylistsPlaylist {
    private boolean collaborative;

    private String description;

    private ExternalUrls external_urls;

    private String href;

    private String id;

    private Image[] images;

    private String name;

    private PlaylistsPlaylistOwner owner;

    @JsonProperty("public")
    private boolean isPublic;
    @JsonProperty("snapshot_id")
    private String snapshot_id;

    private PlaylistsPlaylistTrack tracks;

    private String type;

    private String uri;
}

@JsonIgnoreProperties
@Data
class PlaylistsPlaylistOwner {
    private ExternalUrls external_urls;

    private Followers followers;

    private String href;

    private String id;

    private String type;

    private String uri;
    @JsonProperty("display_name")
    private String display_name;
}

@JsonIgnoreProperties
@Data
class PlaylistsPlaylistTrack {
    private String href;

    private int total;
}

@JsonIgnoreProperties
@Data
class Shows {
    private String href;

    private int limit;

    private String next;

    private int offset;

    private String previous;

    private int total;

    private ShowsShow[] items;


}

@JsonIgnoreProperties
@Data
class ShowsShow {
    private String[] available_markets;

    private Copyright[] copyrights;

    private String description;
    @JsonProperty("html_description")
    private String html_description;

    private boolean explicit;

    private ExternalUrls external_urls;

    private String href;

    private String id;

    private Image[] images;
    @JsonProperty("is_externally_hosted")
    private boolean is_externally_hosted;

    private String[] languages;
    @JsonProperty("media_type")
    private String media_type;

    private String name;

    private String publisher;

    private String type;

    private String uri;
    @JsonProperty("total_episodes")
    private int total_episodes;
}

@JsonIgnoreProperties
@Data
class Episodes {
    private String href;

    private int limit;

    private String next;

    private int offset;

    private String previous;

    private int total;

    private EpisodesEpisode[] items;
}

@JsonIgnoreProperties
@Data
class EpisodesEpisode {
    @JsonProperty("audio_preview_url")
    private String audio_preview_url;

    private String description;
    @JsonProperty("html_description")
    private String html_description;
    @JsonProperty("duration_ms")
    private long duration_ms;

    private boolean explicit;

    private ExternalUrls external_urls;

    private String href;

    private String id;

    private Image[] images;
    @JsonProperty("is_externally_hosted")
    private boolean is_externally_hosted;
    @JsonProperty("is_playable")
    private boolean is_playable;

    private String language;

    private String[] languages;

    private String name;
    @JsonProperty("release_date")
    private String release_date;
    @JsonProperty("release_date_precision")
    private String release_date_precision;

    private EpisodesEpisodeResumePoint resume_point;

    private String type;

    private String uri;

    private Restrictions restrictions;
}

@JsonIgnoreProperties
@Data
class EpisodesEpisodeResumePoint {
    @JsonProperty("fully_played")
    private boolean fully_played;
    @JsonProperty("resume_position_ms")
    private long resume_position_ms;
}

@JsonIgnoreProperties
@Data
class Audiobooks {
    private String href;

    private int limit;

    private String next;

    private int offset;

    private String previous;

    private int total;

    private AudiobooksAudiobook[] items;
}

@JsonIgnoreProperties
@Data
class AudiobooksAudiobook {
    private AudiobooksAudiobookAuthor[] authors;

    private String[] available_markets;

    private Copyright[] copyrights;

    private String description;
    @JsonProperty("html_description")
    private String html_description;

    private String edition;

    private boolean explicit;

    private ExternalUrls external_urls;

    private String href;

    private String id;

    private Image[] images;

    private String[] languages;
    @JsonProperty("media_type")
    private String media_type;

    private String name;

    private AudiobooksAudiobookNarrator[] narrators;

    private String publisher;

    private String type;

    private String uri;
    @JsonProperty("total_chapters")
    private int total_chapters;
}

@JsonIgnoreProperties
@Data
class AudiobooksAudiobookAuthor {
    private String name;
}

@JsonIgnoreProperties
@Data
class AudiobooksAudiobookNarrator {
    private String name;
}

