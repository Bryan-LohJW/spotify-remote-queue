package com.bryan.spotifyremotequeue.service.spotify.response;

import com.bryan.spotifyremotequeue.service.spotify.response.common.ExternalUrls;
import com.bryan.spotifyremotequeue.service.spotify.response.common.TrackObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonIgnoreProperties
@Data
public class PlaybackStateResponse {
    private Device device;

    private String repeat_state;
    @JsonProperty("shuffle_state")
    private boolean shuffle_state;

    private Context context;

    private long timestamp;
    @JsonProperty("progress_ms")
    private long progress_ms;
    @JsonProperty("is_playing")
    private boolean is_playing;

    // possibility of episode object, to handle
    private TrackObject item;
    @JsonProperty("currently_playing_type")
    private String currently_playing_type;

    private Actions actions;
}

@JsonIgnoreProperties
@Data
class Context {
    private String type;

    private String href;

    private ExternalUrls external_urls;

    private String uri;
}

@JsonIgnoreProperties
@Data
class Actions {
    @JsonProperty("interrupting_playback")
    private boolean interrupting_playback;

    private boolean pausing;

    private boolean resuming;

    private boolean seeking;
    @JsonProperty("skipping_next")
    private boolean skipping_next;
    @JsonProperty("skipping_prev")
    private boolean skipping_prev;
    @JsonProperty("toggling_repeat_context")
    private boolean toggling_repeat_context;
    @JsonProperty("toggling_shuffle")
    private boolean toggling_shuffle;
    @JsonProperty("toggling_repeat_track")
    private boolean toggling_repeat_track;
    @JsonProperty("transferring_playback")
    private boolean transferring_playback;
}