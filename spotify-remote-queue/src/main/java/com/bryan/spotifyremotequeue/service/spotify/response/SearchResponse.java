package com.bryan.spotifyremotequeue.service.spotify.response;

import com.bryan.spotifyremotequeue.service.spotify.response.common.ExternalUrls;
import com.bryan.spotifyremotequeue.service.spotify.response.common.Followers;
import com.bryan.spotifyremotequeue.service.spotify.response.common.Image;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class SearchResponse {

    private Tracks tracks;

    private Artists artists;

    private Albums albums;

    private Playlists playlists;

    private Shows shows;

//    private Episodes episodes; // unable to map

    private Audiobooks audiobooks;
}

@Data
class Tracks {
    private String href;

    private int limit;

    private String next;

    private int offset;

    private String previous;

    private int total;

    private TracksTrack[] items;
}

@Data
class TracksTrack {
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
class TracksTrackAlbumArtist {
    private ExternalUrls external_urls;

    private String href;

    private String id;

    private String name;

    private String type;

    private String uri;
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
class Artists {
    private String href;

    private int limit;

    private String next;

    private int offset;

    private String previous;

    private int total;

    private ArtistsArtist[] items;
}

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

@Data
class AlbumsAlbum {
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

    private AlbumsAlbumArtist[] artists;


}

@Data
class AlbumsAlbumArtist {
    private ExternalUrls external_urls;

    private String href;

    private String id;

    private String name;

    private String type;

    private String uri;
}

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

    private String snapshot_id;

    private PlaylistsPlaylistTrack tracks;

    private String type;

    private String uri;
}

@Data
class PlaylistsPlaylistOwner {
    private ExternalUrls external_urls;

    private Followers followers;

    private String href;

    private String id;

    private String type;

    private String uri;

    private String display_name;
}

@Data
class PlaylistsPlaylistTrack {
    private String href;

    private int total;
}

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

@Data
class ShowsShow {
    private String[] available_markets;

    private Copyright[] copyrights;

    private String description;

    private String html_description;

    private boolean explicit;

    private ExternalUrls external_urls;

    private String href;

    private String id;

    private Image[] images;

    private boolean is_externally_hosted;

    private String[] languages;

    private String media_type;

    private String name;

    private String publisher;

    private String type;

    private String uri;

    private int total_episodes;
}

//@Data
//class Episodes {
//    private String href;
//
//    private int limit;
//
//    private String next;
//
//    private int offset;
//
//    private String previous;
//
//    private int total;
//
//    private EpisodesEpisode[] items;
//}
//
//@Data
//class EpisodesEpisode {
//    private String audio_preview_url;
//
//    private String description;
//
//    private String html_description;
//
//    private int duration_ms;
//
//    private boolean explicit;
//
//    private ExternalUrls external_urls;
//
//    private String href;
//
//    private String id;
//
//    private Image[] images;
//
//    private boolean is_externally_hosted;
//
//    private boolean is_playable;
//
//    private String language;
//
//    private String[] languages;
//
//    private String name;
//
//    private String release_date;
//
//    private String release_date_precision;
//
//    private EpisodesEpisodeResumePoint resume_point;
//
//    private String type;
//
//    private String uri;
//
//    private Restrictions restrictions;
//}
//
//@Data
//class EpisodesEpisodeResumePoint {
//    private boolean fully_played;
//
//    private int resume_position_ms;
//}

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

@Data
class AudiobooksAudiobook {
    private AudiobooksAudiobookAuthor[] authors;

    private String[] available_markets;

    private Copyright[] copyrights;

    private String description;

    private String html_description;

    private String edition;

    private boolean explicit;

    private ExternalUrls external_urls;

    private String href;

    private String id;

    private Image[] images;

    private String[] languages;

    private String media_type;

    private String name;

    private AudiobooksAudiobookNarrator[] narrators;

    private String publisher;

    private String type;

    private String uri;

    private int total_chapters;
}

@Data
class AudiobooksAudiobookAuthor {
    private String name;
}

@Data
class AudiobooksAudiobookNarrator {
    private String name;
}

// common
@Data
class ExternalIds {
    private String isrc;

    private String ean;

    private String upc;
}

@Data
class Restrictions {
    private String reason;
}

@Data
class Copyright {
    private String text;

    private String type;
}